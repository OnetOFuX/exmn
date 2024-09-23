class User {
    private int id;
    private String nombre;
    private String apellido;

    public User(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}
