package org.lab.simalsi.solicitud.application;

import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;

public class FileFormData {
    @RestForm("file")
    public File file;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String filename;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String mimetype;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String descripcion;
}
