package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CallCenter {
    // Atributos
    // Relacion agregacion 1 CallCenter tiene 1..* Empleados
    private ArrayList<Empleado> empleados = new ArrayList<>();
   
    // set y get

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    // Metodos logica del negocio
    public ArrayList<Double> liquidarNominaEmp(ArrayList<Empleado> empleados){
        ArrayList<Double> nominas = new ArrayList<>();
        if(empleados==null){
            return null;
        }else{
            // Ciclo Foorloop
            for (Empleado empleado : empleados) {
                double neto;
                
                double valorHoraExtra = empleado.getHorasExtra();
                double salario = empleado.getSalario();
                boolean auxTran = empleado.isAuxilioTransporte();
                double aportes = (salario + valorHoraExtra) * 0.08;

                if(auxTran){
                    neto  = (salario + valorHoraExtra + 106454) - aportes; 
                }else{
                    neto  = (salario + valorHoraExtra) - aportes; 
                }
                
                nominas.add(neto);                
            }
            
        }
        return nominas;
    }
    
    public ArrayList<Double> liquidarPrestacionesEmp(ArrayList<Empleado> empleados){
        ArrayList<Double> prestaciones = new ArrayList<>();
        
        if(empleados==null){
            return null;
        }else{
            
            for (Empleado empleado : empleados) {
                double vlrPrestaciones, primas, cesantias, intCesantias, vacaciones;
                int vlrAuxTrans = 106454;
                double valorHoraExtra = empleado.getHorasExtra();
                double salario = empleado.getSalario();
                boolean auxTran = empleado.isAuxilioTransporte();
                
                // Calcular las prestaciones
                if(auxTran){
                    primas = (salario + valorHoraExtra + vlrAuxTrans) * 0.0833;
                    cesantias = (salario + valorHoraExtra + vlrAuxTrans) * 0.0833;
                    intCesantias = (cesantias * 0.12);
                    vacaciones = (salario * 0.0416);
                }else{
                    primas = (salario + valorHoraExtra) * 0.0833;
                    cesantias = (salario + valorHoraExtra) * 0.0833;
                    intCesantias = (cesantias * 0.12);
                    vacaciones = (salario * 0.0416);
                }
                
                vlrPrestaciones = ( primas + cesantias + intCesantias + vacaciones );
                prestaciones.add(vlrPrestaciones);
            }
        }
        
        return prestaciones;
    }
    
    // (Liquidacion Nomina 1 Empleado) -- Sobre Carga de Metodos
    public ArrayList<Double> liquidarNominaEmp(Empleado empleado){
        ArrayList<Double> nomina = new ArrayList<>();
        if(empleado==null){
            return null;
        }else{
 
            double neto;
            double valorHoraExtra = empleado.getHorasExtra();
            double salario = empleado.getSalario();
            boolean auxTran = empleado.isAuxilioTransporte();
            double aportes = (salario + valorHoraExtra) * 0.08;

            if(auxTran){
                neto  = (salario + valorHoraExtra + 106454) - aportes; 
            }else{
                neto  = (salario + valorHoraExtra) - aportes; 
            }

            nomina.add(neto);                
        }
        return nomina;
    }
    
    public ArrayList<Double> liquidarPrestacionesEmp(Empleado empleado){
        ArrayList<Double> prestacion = new ArrayList<>();
        
        if(empleado==null){
            return null;
        }else{
            
            double vlrPrestaciones, primas, cesantias, intCesantias, vacaciones;
            int vlrAuxTrans = 106454;
            double valorHoraExtra = empleado.getHorasExtra();
            double salario = empleado.getSalario();
            boolean auxTran = empleado.isAuxilioTransporte();

            // Calcular las prestaciones
            if(auxTran){
                primas = (salario + valorHoraExtra + vlrAuxTrans) * 0.0833;
                cesantias = (salario + valorHoraExtra + vlrAuxTrans) * 0.0833;
                intCesantias = (cesantias * 0.12);
                vacaciones = (salario * 0.0416);
            }else{
                primas = (salario + valorHoraExtra) * 0.0833;
                cesantias = (salario + valorHoraExtra) * 0.0833;
                intCesantias = (cesantias * 0.12);
                vacaciones = (salario * 0.0416);
            }

            vlrPrestaciones = ( primas + cesantias + intCesantias + vacaciones );
            prestacion.add(vlrPrestaciones);
        
        }
        
        return prestacion;
    }
    
    public ArrayList<ArrayList> procesoMasivoNomina(LinkedList<Empleado> empleados){
        ArrayList<ArrayList> liqNominasPrestaciones = new ArrayList<>();
   
        if(empleados != null){
            for (Empleado empleado : empleados) {
                //HashMap<String, ArrayList> liqEmpleado = new HashMap<String, ArrayList>();
                
                ArrayList liqEmpleado = new ArrayList<>();

                ArrayList<Double> nomina = new ArrayList<>();
                ArrayList<Double> prestacion = new ArrayList<>();
                   
                int id =  empleado.getId();
                String nombreCompleto = empleado.getNombre() + " " + empleado.getApellidos();
                nomina = liquidarNominaEmp(empleado);
                prestacion = liquidarPrestacionesEmp(empleado);
                
                liqEmpleado.add(id);
                liqEmpleado.add(nombreCompleto);
                liqEmpleado.add(Math.round(nomina.get(0)));
                liqEmpleado.add(Math.round(prestacion.get(0)));

                liqNominasPrestaciones.add(liqEmpleado);          
            }
        }else{
            return null;
        }        
        return liqNominasPrestaciones;
    };
    
    
}
