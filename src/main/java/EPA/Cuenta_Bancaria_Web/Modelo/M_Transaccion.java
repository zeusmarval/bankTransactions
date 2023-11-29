package EPA.Cuenta_Bancaria_Web.Modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "m_transaccion")
public class M_Transaccion
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "monto_transaccion", nullable = false)
    private BigDecimal monto_transaccion;
    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldo_inicial;
    @Column(name = "saldo_final", nullable = false)
    private BigDecimal saldo_final;
    @Column(name = "costo_transaccion", nullable = false)
    private BigDecimal costo_transaccion;
    @Column(name = "tipo", nullable = false)
    private String tipo;

    //-------------------------------------------------------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    @JsonBackReference
    private M_Cuenta cuenta;

    //-------------------------------------------------------------------------------------------------------------------------

    public M_Transaccion(String id, M_Cuenta cuenta, BigDecimal monto_transaccion, BigDecimal saldo_inicial, BigDecimal saldo_final, BigDecimal costo_tansaccion, String tipo) {
        this.id = id;
        this.cuenta = cuenta;
        this.monto_transaccion = monto_transaccion;
        this.saldo_inicial = saldo_inicial;
        this.saldo_final = saldo_final;
        this.costo_transaccion = costo_tansaccion;
        this.tipo = tipo;
    }

    public M_Transaccion()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public M_Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(M_Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getMonto_transaccion() {
        return monto_transaccion;
    }

    public void setMonto_transaccion(BigDecimal monto_transaccion) {
        this.monto_transaccion = monto_transaccion;
    }

    public BigDecimal getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(BigDecimal saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public BigDecimal getSaldo_final() {
        return saldo_final;
    }

    public void setSaldo_final(BigDecimal saldo_final) {
        this.saldo_final = saldo_final;
    }

    public BigDecimal getCosto_tansaccion() {
        return costo_transaccion;
    }

    public void setCosto_tansaccion(BigDecimal costo_tansaccion) {
        this.costo_transaccion = costo_tansaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
