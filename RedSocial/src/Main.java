import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RedSocial red = new RedSocial();
        Usuario usuarioActual = null;
        boolean ejecutando = true;

        while (ejecutando) {
            if (usuarioActual == null) {
                System.out.println("\n===== RED SOCIAL =====");
                System.out.println("1. Registrarse");
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Ver todos los usuarios");
                System.out.println("0. Salir");

                int opcion = leerOpcion(sc, 0, 3);

                switch (opcion) {
                    case 1:
                        System.out.print("Nuevo nombre de usuario: ");
                        String nuevoNombre = sc.nextLine().trim();

                        System.out.print("Nueva contraseña: ");
                        String nuevaPass = sc.nextLine().trim();

                        if (nuevoNombre.isEmpty() || nuevaPass.isEmpty()) {
                            System.out.println("Nombre y contraseña no pueden estar vacíos.");
                        } else if (red.registrarUsuario(nuevoNombre, nuevaPass)) {
                            System.out.println("Usuario registrado correctamente.");
                        } else {
                            System.out.println("Ese nombre de usuario ya existe.");
                        }
                        break;

                    case 2:
                        System.out.print("Nombre de usuario: ");
                        String nombreLogin = sc.nextLine().trim();

                        System.out.print("Contraseña: ");
                        String passLogin = sc.nextLine().trim();

                        usuarioActual = red.login(nombreLogin, passLogin);

                        if (usuarioActual != null) {
                            System.out.println("Bienvenido, " + usuarioActual.getNombre() + ".");
                        } else {
                            System.out.println("Credenciales incorrectas.");
                        }
                        break;

                    case 3:
                        red.mostrarTodosLosUsuarios();
                        break;

                    case 0:
                        System.out.println("Saliendo del programa...");
                        ejecutando = false;
                        break;
                }

            } else {
                System.out.println("\n===== SESIÓN DE " + usuarioActual.getNombre().toUpperCase() + " =====");
                System.out.println("1. Ver mi perfil/red");
                System.out.println("2. Ver todos los usuarios");
                System.out.println("3. Agregar amigo");
                System.out.println("4. Eliminar amigo");
                System.out.println("5. Ver amigos en común con otro usuario");
                System.out.println("6. Ver sugerencias de amistad");
                System.out.println("7. Cerrar sesión");

                int opcion = leerOpcion(sc, 1, 7);

                switch (opcion) {
                    case 1:
                        red.mostrarMiRed(usuarioActual);
                        break;

                    case 2:
                        red.mostrarTodosLosUsuarios();
                        break;

                    case 3:
                        ArrayList<Usuario> disponibles = red.obtenerUsuariosDisponiblesParaAgregar(usuarioActual);
                        red.mostrarListaUsuarios(disponibles, "USUARIOS DISPONIBLES PARA AGREGAR");

                        if (!disponibles.isEmpty()) {
                            System.out.println("0. Cancelar");
                            int opAgregar = leerOpcion(sc, 0, disponibles.size());

                            if (opAgregar != 0) {
                                Usuario seleccionado = red.obtenerUsuarioPorIndice(disponibles, opAgregar - 1);
                                red.agregarAmistad(usuarioActual.getNombre(), seleccionado.getNombre());
                            }
                        }
                        break;

                    case 4:
                        ArrayList<Usuario> amigos = red.obtenerAmigosDeUsuario(usuarioActual);
                        red.mostrarListaUsuarios(amigos, "TUS AMIGOS");

                        if (!amigos.isEmpty()) {
                            System.out.println("0. Cancelar");
                            int opEliminar = leerOpcion(sc, 0, amigos.size());

                            if (opEliminar != 0) {
                                Usuario seleccionado = red.obtenerUsuarioPorIndice(amigos, opEliminar - 1);
                                red.eliminarAmistad(usuarioActual.getNombre(), seleccionado.getNombre());
                            }
                        }
                        break;

                    case 5:
                        ArrayList<Usuario> otrosUsuarios = new ArrayList<>();

                        for (Usuario u : red.getUsuarios()) {
                            if (!u.getNombre().equalsIgnoreCase(usuarioActual.getNombre())) {
                                otrosUsuarios.add(u);
                            }
                        }

                        red.mostrarListaUsuarios(otrosUsuarios, "USUARIOS PARA COMPARAR");

                        if (!otrosUsuarios.isEmpty()) {
                            System.out.println("0. Cancelar");
                            int opComun = leerOpcion(sc, 0, otrosUsuarios.size());

                            if (opComun != 0) {
                                Usuario seleccionado = red.obtenerUsuarioPorIndice(otrosUsuarios, opComun - 1);
                                red.mostrarAmigosEnComun(usuarioActual, seleccionado.getNombre());
                            }
                        }
                        break;

                    case 6:
                        red.sugerirAmigos(usuarioActual);
                        break;

                    case 7:
                        System.out.println("Sesión cerrada.");
                        usuarioActual = null;
                        break;
                }
            }
        }

        sc.close();
    }

    public static int leerOpcion(Scanner sc, int min, int max) {
        int opcion;

        while (true) {
            System.out.print("Elige una opción: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();

                if (opcion >= min && opcion <= max) {
                    return opcion;
                }
            } else {
                sc.nextLine();
            }

            System.out.println("Opción inválida. Intenta otra vez.");
        }
    }
}