package EPA.Cuenta_Bancaria_Web.Modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "m_cuenta")
public class M_Cuenta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "saldo_Global", nullable = false)
    private BigDecimal saldo_Global;

    //-------------------------------------------------------------------------------------------------------------- (Relacion)
    //@OneToOne(mappedBy = "cuenta")
    @OneToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private M_Cliente cliente;

    @OneToMany(mappedBy = "cuenta")
    @JsonManagedReference
    private List<M_Transaccion> transacciones;

    //--------------------------------------------------------------------------------------------------------------

    public M_Cuenta(String id, M_Cliente cliente, BigDecimal saldo_Global)
    {
        this.id = id;
        this.cliente = cliente;
        this.saldo_Global = saldo_Global;
    }

    public M_Cuenta()
    {

    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public M_Cliente getCliente() {
        return cliente;
    }

    public void setCliente(M_Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getSaldo_Global() {
        return saldo_Global;
    }

    public void setSaldo_Global(BigDecimal saldo_Global) {
        this.saldo_Global = saldo_Global;
    }

    //----------------------------------------

    @Override
    public String toString()
    {
        return "[Detalle Cuenta Bancaria] \n" +
                "Id Cuenta: " + id + "\n" +
                "Cliente: " + cliente.getNombre() + "\n" +
                "Saldo Final: " + saldo_Global +
                "\n";
    }
}
