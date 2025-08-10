package org.lab.simalsi.solicitud.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;
import org.lab.simalsi.solicitud.application.CrearResultadoCGODto;
import org.lab.simalsi.solicitud.application.FileFormData;
import org.lab.simalsi.solicitud.application.ResultadoCGOService;
import org.lab.simalsi.solicitud.models.ArchivoAdjunto;
import org.lab.simalsi.solicitud.models.ResultadoCGO;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Path("/resultado/cgo")
public class ResultadoCGOResource {

    @Inject
    ResultadoCGOService resultadoCGOService;

    @POST
    @Path("/{solicitudId}")
    @Transactional
    public ResultadoCGO store(@RestPath Long solicitudId, CrearResultadoCGODto resultadoCGODto) {
        return resultadoCGOService.crearResultadoCGO(solicitudId, resultadoCGODto);
    }

    @POST
    @Path("/upload/{resultadoId}")
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@RestPath Long resultadoId, FileFormData formData) throws Exception {
        ArchivoAdjunto archivoAdjunto = resultadoCGOService.asociarArchivo(resultadoId, formData);

        return Response.ok(archivoAdjunto).build();
    }

    @GET
    @Path("/download/{objectkey}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response descargarArchivo(@RestPath String objectkey) {
        ResponseBytes<GetObjectResponse> objectBytes = resultadoCGOService.obtenerArchivoPorNombre(objectkey);
        Response.ResponseBuilder response = Response.ok(objectBytes.asByteArray());
        response.header("Content-Disposition", "inline;filename=" + objectkey);
        response.header("Content-Type", objectBytes.response().contentType());
        return response.build();
    }
}
