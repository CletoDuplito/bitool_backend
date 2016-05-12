package PO;

public class JSONTester {

  private String dimension;
  private Data data;

  
  
  public JSONTester(Data data){
    this.dimension = "Product,Store,Date_Time";
    this.data = data;
  }



  public String getDimension() {
    return dimension;
  }



  public void setDimension(String dimension) {
    this.dimension = dimension;
  }

  public String getDataVaue() {
    return this.data.getValue();
  }

  public String getDataKey() {
    return this.data.getKey();
  }



  public void setData(Data data) {
    this.data = data;
  }

  

  

  
}

