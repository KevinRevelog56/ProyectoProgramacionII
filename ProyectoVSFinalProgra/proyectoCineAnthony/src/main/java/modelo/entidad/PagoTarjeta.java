package modelo.entidad;

public class PagoTarjeta implements IPago {

    @Override
    public boolean pagar(double monto) {
        //pago con tarjeta
        System.out.println("Procesando cobro con tarjeta de crédito/débito por un total de: $" + monto);
        boolean transaccionExitosa = true; 
        
        return transaccionExitosa;
    }

    @Override
    public String obtenerNombreMetodo() {
        return "Tarjeta";
    }
}