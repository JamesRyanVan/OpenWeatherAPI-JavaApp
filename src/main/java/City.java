public class City {
    
    private int cityId;
    private String cityName;
    private String countryName;
    
    public City(int cityId, String cityName, String countryName) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryName = countryName;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }
    
    public String toString() {
    	return cityName + ", " + countryName;
    }
}
