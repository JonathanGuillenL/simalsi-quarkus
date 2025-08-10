package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.solicitud.infrastructure.ArchivoAdjuntoRepository;
import org.lab.simalsi.solicitud.infrastructure.ResultadoCGORepository;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;
import org.lab.simalsi.solicitud.models.ArchivoAdjunto;
import org.lab.simalsi.solicitud.models.ResultadoCGO;
import org.lab.simalsi.solicitud.models.SolicitudCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

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
    ResultadoMapper resultadoMapper;

    public ResultadoCGO crearResultadoCGO(Long solicitudId, CrearResultadoCGODto resultadoCGODto) {
        ResultadoCGO resultadoCGO = resultadoMapper.toModel(resultadoCGODto);

        resultadoCGO.setId(solicitudId);
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
            .orElseThrow(() -> new NotFoundException("Resultado no encontrado"));

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
        archivoAdjunto.setActivo(true);

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
}
