/**
 * CatalogServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services.catalog;

public class CatalogServiceLocator extends org.apache.axis.client.Service implements services.catalog.CatalogService {

    public CatalogServiceLocator() {
    }


    public CatalogServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CatalogServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Catalog
    private java.lang.String Catalog_address = "http://localhost:8080/Bookstore/services/Catalog";

    public java.lang.String getCatalogAddress() {
        return Catalog_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CatalogWSDDServiceName = "Catalog";

    public java.lang.String getCatalogWSDDServiceName() {
        return CatalogWSDDServiceName;
    }

    public void setCatalogWSDDServiceName(java.lang.String name) {
        CatalogWSDDServiceName = name;
    }

    public services.catalog.Catalog getCatalog() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Catalog_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCatalog(endpoint);
    }

    public services.catalog.Catalog getCatalog(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            services.catalog.CatalogSoapBindingStub _stub = new services.catalog.CatalogSoapBindingStub(portAddress, this);
            _stub.setPortName(getCatalogWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCatalogEndpointAddress(java.lang.String address) {
        Catalog_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (services.catalog.Catalog.class.isAssignableFrom(serviceEndpointInterface)) {
                services.catalog.CatalogSoapBindingStub _stub = new services.catalog.CatalogSoapBindingStub(new java.net.URL(Catalog_address), this);
                _stub.setPortName(getCatalogWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Catalog".equals(inputPortName)) {
            return getCatalog();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://catalog.services", "CatalogService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://catalog.services", "Catalog"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Catalog".equals(portName)) {
            setCatalogEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
