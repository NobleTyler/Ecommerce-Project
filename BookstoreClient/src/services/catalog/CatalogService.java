/**
 * CatalogService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services.catalog;

public interface CatalogService extends javax.xml.rpc.Service {
    public java.lang.String getCatalogAddress();

    public services.catalog.Catalog getCatalog() throws javax.xml.rpc.ServiceException;

    public services.catalog.Catalog getCatalog(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
