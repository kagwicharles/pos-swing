package Model;

public class ProductModel {

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	private String productCode;
	private String productName;
	private String productPrice;

	public ProductModel(String productCode, String productName, String productPrice) {
		this.productCode = productCode;
		this.productName = productName;
		this.productPrice = productPrice;
	}

}
