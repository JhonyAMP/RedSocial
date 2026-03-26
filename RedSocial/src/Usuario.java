import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String password;
    private ArrayList<String> amigos;

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
        this.amigos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getAmigos() {
        return amigos;
    }

    public void agregarAmigo(String amigo) {
        if (!amigo.equalsIgnoreCase(nombre) && !amigos.contains(amigo)) {
            amigos.add(amigo);
        }
    }

    public void eliminarAmigo(String amigo) {
        amigos.remove(amigo);
    }

    public ArrayList<String> amigosEnComun(Usuario otro) {
        ArrayList<String> comunes = new ArrayList<>(this.amigos);
        comunes.retainAll(otro.getAmigos());
        return comunes;
    }

    public void mostrarPerfil() {
        System.out.println("\n=== MI PERFIL ===");
        System.out.println("Usuario: " + nombre);
        System.out.println("Amigos: " + amigos);
    }
}
