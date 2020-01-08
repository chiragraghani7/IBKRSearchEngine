class DataJson {
  private String data;

  DataJson() {
    this.data = "user_adhar_id: '1000', user_name: 'chirag raghani', user_email:'chirag@gmail.com',age:'20',country:'India'\n"
        + "user_adhar_id: '1001', user_name: 'nikhil ghind', user_email:'nikhil@gmail.com',age:'21',country:'India'\n"
        + "user_adhar_id: '1002', user_name: 'jatin sumai', user_email:'jatin@gmail.com',age:'21', country:'India'\n"
        + "user_adhar_id: '1003', user_name: 'akshay navani', user_email:'akshay@gmail.com',age:'21',country:'India'\n"
        + "user_adhar_id: '1004', user_name: 'akash narang', user_email:'akash@gmail.com',age:'20',country:'India'\n";
  }

  public String getData() {
    return new String(this.data);
  }

}