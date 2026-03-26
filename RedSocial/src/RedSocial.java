import java.util.ArrayList;

public class RedSocial {
    private ArrayList<Usuario> usuarios;

    public RedSocial() {
        usuarios = new ArrayList<>();
        cargarUsuariosIniciales();
    }

    private void cargarUsuariosIniciales() {
        Usuario ana = new Usuario("Ana", "1234");
        Usuario luis = new Usuario("Luis", "1234");
        Usuario pedro = new Usuario("Pedro", "1234");
        Usuario maria = new Usuario("Maria", "1234");
        Usuario sofia = new Usuario("Sofia", "1234");
        Usuario carlos = new Usuario("Carlos", "1234");

        usuarios.add(ana);
        usuarios.add(luis);
        usuarios.add(pedro);
        usuarios.add(maria);
        usuarios.add(sofia);
        usuarios.add(carlos);

        agregarAmistadInterna("Ana", "Luis");
        agregarAmistadInterna("Ana", "Maria");
        agregarAmistadInterna("Luis", "Pedro");
        agregarAmistadInterna("Maria", "Sofia");
        agregarAmistadInterna("Pedro", "Carlos");
        agregarAmistadInterna("Sofia", "Carlos");
    }

    private void agregarAmistadInterna(String nombre1, String nombre2) {
        Usuario u1 = buscarUsuario(nombre1);
        Usuario u2 = buscarUsuario(nombre2);

        if (u1 != null && u2 != null) {
            u1.agregarAmigo(u2.getNombre());
            u2.agregarAmigo(u1.getNombre());
        }
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario buscarUsuario(String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }

    public boolean registrarUsuario(String nombre, String password) {
        if (buscarUsuario(nombre) != null) {
            return false;
        }

        usuarios.add(new Usuario(nombre, password));
        return true;
    }

    public Usuario login(String nombre, String password) {
        Usuario usuario = buscarUsuario(nombre);

        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }

        return null;
    }

    public void mostrarTodosLosUsuarios() {
        System.out.println("\n=== TODOS LOS USUARIOS ===");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
        }
    }

    public void mostrarMiRed(Usuario actual) {
        actual.mostrarPerfil();
    }

    public void agregarAmistad(String usuarioActual, String otroUsuario) {
        Usuario u1 = buscarUsuario(usuarioActual);
        Usuario u2 = buscarUsuario(otroUsuario);

        if (u1 == null || u2 == null) {
            System.out.println("Uno de los usuarios no existe.");
            return;
        }

        if (u1.getNombre().equalsIgnoreCase(u2.getNombre())) {
            System.out.println("No puedes agregarte a ti mismo.");
            return;
        }

        if (u1.getAmigos().contains(u2.getNombre())) {
            System.out.println("Ya son amigos.");
            return;
        }

        u1.agregarAmigo(u2.getNombre());
        u2.agregarAmigo(u1.getNombre());
        System.out.println("Amistad agregada correctamente.");
    }

    public void eliminarAmistad(String usuarioActual, String otroUsuario) {
        Usuario u1 = buscarUsuario(usuarioActual);
        Usuario u2 = buscarUsuario(otroUsuario);

        if (u1 == null || u2 == null) {
            System.out.println("Uno de los usuarios no existe.");
            return;
        }

        if (!u1.getAmigos().contains(u2.getNombre())) {
            System.out.println("No eran amigos.");
            return;
        }

        u1.eliminarAmigo(u2.getNombre());
        u2.eliminarAmigo(u1.getNombre());
        System.out.println("Amistad eliminada correctamente.");
    }

    public void mostrarAmigosEnComun(Usuario actual, String otroNombre) {
        Usuario otro = buscarUsuario(otroNombre);

        if (otro == null) {
            System.out.println("Ese usuario no existe.");
            return;
        }

        ArrayList<String> comunes = actual.amigosEnComun(otro);
        System.out.println("Amigos en común con " + otro.getNombre() + ": " + comunes);
    }

    public void sugerirAmigos(Usuario actual) {
        ArrayList<String> sugeridos = new ArrayList<>();

        for (String nombreAmigo : actual.getAmigos()) {
            Usuario amigo = buscarUsuario(nombreAmigo);

            if (amigo != null) {
                for (String amigoDeAmigo : amigo.getAmigos()) {
                    boolean noSoyYo = !amigoDeAmigo.equalsIgnoreCase(actual.getNombre());
                    boolean noEsAmigoDirecto = !actual.getAmigos().contains(amigoDeAmigo);
                    boolean noRepetido = !sugeridos.contains(amigoDeAmigo);

                    if (noSoyYo && noEsAmigoDirecto && noRepetido) {
                        sugeridos.add(amigoDeAmigo);
                    }
                }
            }
        }

        System.out.println("Sugerencias para " + actual.getNombre() + ": " + sugeridos);
    }

    public ArrayList<Usuario> obtenerUsuariosDisponiblesParaAgregar(Usuario actual) {
        ArrayList<Usuario> disponibles = new ArrayList<>();

        for (Usuario u : usuarios) {
            boolean noSoyYo = !u.getNombre().equalsIgnoreCase(actual.getNombre());
            boolean noEsAmigo = !actual.getAmigos().contains(u.getNombre());

            if (noSoyYo && noEsAmigo) {
                disponibles.add(u);
            }
        }

        return disponibles;
    }

    public ArrayList<Usuario> obtenerAmigosDeUsuario(Usuario actual) {
        ArrayList<Usuario> amigosUsuario = new ArrayList<>();

        for (String nombreAmigo : actual.getAmigos()) {
            Usuario amigo = buscarUsuario(nombreAmigo);
            if (amigo != null) {
                amigosUsuario.add(amigo);
            }
        }

        return amigosUsuario;
    }

    public Usuario obtenerUsuarioPorIndice(ArrayList<Usuario> lista, int indice) {
        if (indice >= 0 && indice < lista.size()) {
            return lista.get(indice);
        }
        return null;
    }

    public void mostrarListaUsuarios(ArrayList<Usuario> lista, String titulo) {
        System.out.println("\n=== " + titulo + " ===");

        if (lista.isEmpty()) {
            System.out.println("No hay usuarios disponibles.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNombre());
        }
    }
}