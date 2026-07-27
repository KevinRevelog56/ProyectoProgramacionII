package modelo.entidad;

public class PagoEfectivo implements IPago {

    @Override
    public boolean pagar(double monto) {
        //pago en mostrador
        System.out.println("Pago en efectivo seleccionado en mostrador. Monto a cancelar: $" + monto);
        return true; 
    }

    @Override
    public String obtenerNombreMetodo() {
        return "Efectivo (Mostrador)";
    }
}