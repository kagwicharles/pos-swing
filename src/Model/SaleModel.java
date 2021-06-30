package Model;

public class SaleModel {

	public SaleModel(String noOfItems, String totalPaid, String servedBy) {
		this.noOfItems = noOfItems;
		this.totalPaid = totalPaid;
		this.servedBy = servedBy;
	}

	private String noOfItems;

	public String getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(String noOfItems) {
		this.noOfItems = noOfItems;
	}

	public String getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(String totalPaid) {
		this.totalPaid = totalPaid;
	}

	public String getServedBy() {
		return servedBy;
	}

	public void setServedBy(String servedBy) {
		this.servedBy = servedBy;
	}

	private String totalPaid;
	private String servedBy;
}
