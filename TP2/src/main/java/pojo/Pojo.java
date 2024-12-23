package pojo;
import java.time.LocalDate;

public class Pojo {

private String name="";
private float price=(float)0.5;
private LocalDate sup_date=LocalDate.now();
private int sales=0;
private int total=0;

public String getName() {
	return name;
}

public void setCof_name(String cof_name) {
	this.name = cof_name;
}

public float getPrice() {
	return price;
}

public void setPrice(float price) {
	this.price = price;
}

public LocalDate getSup_date() {
	return sup_date;
}

public void setSup_date(LocalDate sup_date) {
	this.sup_date = sup_date;
}

public int getSales() {
	return sales;
}

public void setSales(int sales) {
	this.sales = sales;
}

public int getTotal() {
	return total;
}

public void setTotal(int total) {
	this.total = total;
}

}
