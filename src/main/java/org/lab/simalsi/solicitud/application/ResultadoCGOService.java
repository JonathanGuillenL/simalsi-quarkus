package org.lab.simalsi.solicitud.application;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.solicitud.infrastructure.ArchivoAdjuntoRepository;
import org.lab.simalsi.solicitud.infrastructure.CodigoEnfermedadesRepository;
import org.lab.simalsi.solicitud.infrastructure.ResultadoCGORepository;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;
import org.lab.simalsi.solicitud.models.*;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ResultadoCGOService {

    @Inject
    private S3Client s3Client;

    @ConfigProperty(name = "bucket.name")
    private String bucketName;

    @Inject
    ResultadoCGORepository resultadoCGORepository;

    @Inject
    private ArchivoAdjuntoRepository archivoAdjuntoRepository;

    @Inject
    SolicitudCGORepository solicitudCGORepository;

    @Inject
    private ColaboradorRepository colaboradorRepository;

    @Inject
    private CodigoEnfermedadesRepository codigoEnfermedadesRepository;

    @Inject
    ResultadoMapper resultadoMapper;

    public ResultadoCGO crearResultadoCGO(Long solicitudId, String userId, CrearResultadoCGODto resultadoCGODto) {
        ResultadoCGO resultadoCGO = resultadoMapper.toModel(resultadoCGODto);

        Colaborador patologo = colaboradorRepository.findColaboradorByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Patólogo no encontrado"));

        CodigoEnfermedades codigoEnfermedades;
        if (resultadoCGODto.cie() != null) {
            codigoEnfermedades = codigoEnfermedadesRepository.findByIdOptional(resultadoCGODto.cie())
                .orElseThrow(() -> new NotFoundException("CIE no encontrado"));
            resultadoCGO.setCodigoEnfermedades(codigoEnfermedades);
        }

        resultadoCGO.setId(solicitudId);
        resultadoCGO.setPatologo(userId);
        resultadoCGORepository.persist(resultadoCGO);

        SolicitudCGO solicitudCGO = solicitudCGORepository.findByIdOptional(solicitudId)
            .orElseThrow(() -> new NotFoundException("Solicitud no encontrada"));

        solicitudCGO.setResultadoCGO(resultadoCGO);
        solicitudCGO.setEstado(SolicitudEstado.FINALIZADO);
        solicitudCGORepository.persist(solicitudCGO);

        return resultadoCGO;
    }

    public ArchivoAdjunto asociarArchivo(Long resultadoId, FileFormData formData) {
        ResultadoCGO resultadoCGO = resultadoCGORepository.findByIdOptional(resultadoId)
            .orElseThrow(() -> new NotFoundException("Resultado no encontrado."));

        PutObjectRequest putRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(formData.filename)
            .contentType(formData.mimetype)
            .build();

        PutObjectResponse putResponse = s3Client.putObject(putRequest, RequestBody.fromFile(formData.file));
        if (putResponse == null) {
            return null;
        }

        ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
        archivoAdjunto.setUbicacion(formData.filename);
        archivoAdjunto.setDescripcion(formData.descripcion);

        resultadoCGO.addArchivoAdjunto(archivoAdjunto);
        resultadoCGORepository.persist(resultadoCGO);

        return archivoAdjunto;
    }

    public ResponseBytes<GetObjectResponse> obtenerArchivoPorNombre(String objectkey) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(objectkey)
            .build();

        return s3Client.getObjectAsBytes(getRequest);
    }

    public ArchivoAdjunto deshabilitarArchivo(Long id) {
        ArchivoAdjunto archivoAdjunto = archivoAdjuntoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Archivo no encontrado."));

        archivoAdjunto.setDeletedAt(LocalDateTime.now());
        archivoAdjuntoRepository.persist(archivoAdjunto);

        return archivoAdjunto;
    }

    public byte[] generarResultadoPdf(Long solicitudId) {
        HashMap<String, Object> params = new HashMap<>();

        SolicitudCGO solicitudCGO = solicitudCGORepository.findByIdOptional(solicitudId)
            .orElseThrow(() -> new NotFoundException("Solicitud CGO no encontrada."));

        Colaborador recepcionista = colaboradorRepository.findColaboradorByUsername(solicitudCGO.getRecepcionista())
            .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));

        Colaborador patologo = colaboradorRepository.findColaboradorByUsername(solicitudCGO.getResultadoCGO().getPatologo())
            .orElseThrow(() -> new NotFoundException("Patólogo no encontrado"));

        ResultadoJRDataSource dataSource = new ResultadoJRDataSource(List.of(solicitudCGO), recepcionista, patologo);

        ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("reportes/resultado_examen.jasper")) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException | IOException e) {
            Log.error(e.getMessage());
            Log.error(e.getCause());
            return null;
        }
    }
}
