package modelo;
import java.sql.*;

import controlador.Empleado;
import java.util.LinkedList;


public class modelEmpleado extends DbData {
    
    public void mostrarDatos(int empId, String nombre, String apellido, int vlrHoraExtra, String auxTransp, int salarioEmpl){
        System.out.println("ID empleado: " + empId + 
                                   "\nNombres empleado: "+ nombre +
                                   "\nApellidos empleado: "+ apellido +
                                   "\nHoras Extras: "  + vlrHoraExtra +
                                   "\nAuxilio Transporte: " + auxTransp +
                                   "\nSalario: " + salarioEmpl);
        System.out.println("");
    }
    
    public Empleado consultaEmpleadoId(String id){
        Empleado emp = null;
        
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword())) {
            
            String query = "SELECT * FROM `tb_empleado` WHERE id = ? ORDER BY id";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet resul = stmt.executeQuery();
            while(resul.next()){
                
                int empId = resul.getInt(1);
                String nombre = resul.getString(2);
                String apellido = resul.getString(7);
                int vlrHoraExtra = resul.getInt(3);
                String auxTransp = resul.getNString(4);
                int salarioEmpl = resul.getInt(5);
                
                mostrarDatos(empId,nombre,apellido,vlrHoraExtra,auxTransp,salarioEmpl);
                
                switch (auxTransp) {
                    case "true" -> emp = new Empleado(empId,nombre, apellido ,vlrHoraExtra, true, salarioEmpl);
                    case "false" -> emp = new Empleado(empId,nombre, apellido ,vlrHoraExtra, false, salarioEmpl);
                    default -> throw new AssertionError();
                }
            }
            
            stmt.executeUpdate();
            stmt.close();
            
            return emp;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());  
        }
        return emp;
    }  
    
    public void crearEmpleado(String nombre, String apellido, int vlrHoraExtra, String auxTransp, int salarioEmpl){
        
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword())) {
            
            String query = "INSERT INTO `tb_empleado`(`nombre`, `horas_extra`, `aux_transp`, `salario`, `apellido`) "
                         + "VALUES (?,?,?,?,?)";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            
            stmt.setString(1, nombre);
            stmt.setInt(2, vlrHoraExtra);
            stmt.setString(3, auxTransp);
            stmt.setInt(4, salarioEmpl);
            stmt.setString(5, apellido);
            
            stmt.executeUpdate();
            stmt.close();
           
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());  
        }
        
    }
    
    public Empleado actualizarEmpleado(String nombre, String apellido, int vlrHoraExtra, String auxTransp, int salarioEmpl, String id){
       
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword())) {
            
            Empleado empl = new Empleado();
            empl = consultaEmpleadoId(id);
            
            System.out.println("AuxTra" + auxTransp);
            
            if(empl==null){
                System.out.println("Empleado no existe");  
                return empl;
            }else{
                String query = "UPDATE `tb_empleado` "
                     + "SET `nombre`= ? ,`horas_extra`= ? ,`aux_transp`= ? ,`salario`= ?,`apellido`= ?"
                     + "WHERE id = ?";
            
                PreparedStatement stmt = connection.prepareStatement(query);

                stmt.setString(1, nombre);
                stmt.setInt(2, vlrHoraExtra);
                stmt.setString(3, auxTransp);
                stmt.setInt(4, salarioEmpl);
                stmt.setString(5, apellido);
                stmt.setString(6, id);

                stmt.executeUpdate();
                stmt.close();
                return empl;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());  
        }
        
        return null;
    }
    
    public void eliminarEmpleado(String id){
        
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword())) {
            
            String query = "DELETE FROM `tb_empleado` WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
            stmt.close();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());  
        }
    }
    
    public LinkedList<Empleado> ListaEmpleado(){
        LinkedList<Empleado> empleadoLista = new LinkedList<>();
        Empleado empleado = null;
        
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword())) {
            
            String query = "SELECT `id`, `nombre`, `apellido`, `horas_extra`, `aux_transp`, `salario` FROM `tb_empleado` ORDER BY id";
            Statement stmt = connection.createStatement();
            ResultSet resul = stmt.executeQuery(query);
            while(resul.next()){
                int id = resul.getInt(1);
                String nombre = resul.getString(2);
                String apellido = resul.getString(3);
                int vlrHoraExtra = resul.getInt(4);
                String auxTransp = resul.getString(5);
                int salarioEmpl = resul.getInt(6);
                
                switch (auxTransp) {
                    case "true" -> empleado = new Empleado(id,nombre, apellido ,vlrHoraExtra, true, salarioEmpl);
                    case "false" -> empleado = new Empleado(id,nombre, apellido ,vlrHoraExtra, false, salarioEmpl);
                    default -> throw new AssertionError();
                }
                //mostrarDatos(id, nombre, apellido, vlrHoraExtra, auxTransp, salarioEmpl);
                empleadoLista.add(empleado);
            }            
            
            stmt.close();
            resul.close();
            
            return empleadoLista;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return empleadoLista;
    }
}
