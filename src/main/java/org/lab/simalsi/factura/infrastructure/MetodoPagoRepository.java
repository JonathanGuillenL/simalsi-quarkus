package org.lab.simalsi.factura.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.models.MetodoPago;

@ApplicationScoped
public class MetodoPagoRepository implements PanacheRepository<MetodoPago> {
}
