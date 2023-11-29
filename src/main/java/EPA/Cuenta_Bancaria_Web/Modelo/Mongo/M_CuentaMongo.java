package EPA.Cuenta_Bancaria_Web.Modelo.Mongo;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cliente;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Transaccion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("M_CuentaMongo")
public class M_CuentaMongo
{
    @Id
    private String id;
    private BigDecimal saldo_Global;
    private M_ClienteMongo cliente;

    public M_CuentaMongo(String id, M_ClienteMongo cliente, BigDecimal saldo_Global) {
        this.id = id;
        this.saldo_Global = saldo_Global;
        this.cliente = cliente;
    }

    public M_CuentaMongo()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSaldo_Global() {
        return saldo_Global;
    }

    public void setSaldo_Global(BigDecimal saldo_Global) {
        this.saldo_Global = saldo_Global;
    }

    public M_ClienteMongo getCliente() {
        return cliente;
    }

    public void setCliente(M_ClienteMongo cliente) {
        this.cliente = cliente;
    }
}
