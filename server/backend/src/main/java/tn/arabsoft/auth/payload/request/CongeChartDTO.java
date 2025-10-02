package tn.arabsoft.auth.payload.request;

public class CongeChartDTO {
	  private Long congeCount;
	    private String monthYear;
		public CongeChartDTO(Long congeCount, String monthYear) {
			super();
			this.congeCount = congeCount;
			this.monthYear = monthYear;
		}
		public Long getCongeCount() {
			return congeCount;
		}
		public void setCongeCount(Long congeCount) {
			this.congeCount = congeCount;
		}
		public String getMonthYear() {
			return monthYear;
		}
		public void setMonthYear(String monthYear) {
			this.monthYear = monthYear;
		}
	    
	    

}
