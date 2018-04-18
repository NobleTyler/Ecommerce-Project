package services.catalog;

public class CatalogProxy implements services.catalog.Catalog {
  private String _endpoint = null;
  private services.catalog.Catalog catalog = null;
  
  public CatalogProxy() {
    _initCatalogProxy();
  }
  
  public CatalogProxy(String endpoint) {
    _endpoint = endpoint;
    _initCatalogProxy();
  }
  
  private void _initCatalogProxy() {
    try {
      catalog = (new services.catalog.CatalogServiceLocator()).getCatalog();
      if (catalog != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)catalog)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)catalog)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (catalog != null)
      ((javax.xml.rpc.Stub)catalog)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public services.catalog.Catalog getCatalog() {
    if (catalog == null)
      _initCatalogProxy();
    return catalog;
  }
  
  public java.lang.String getProductInfo(int productId) throws java.rmi.RemoteException{
    if (catalog == null)
      _initCatalogProxy();
    return catalog.getProductInfo(productId);
  }
  
  
}