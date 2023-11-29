package EPA.Cuenta_Bancaria_Web.Modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "m_cliente")
public class M_Cliente
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    //-------------------------------------------------------------------------------------------------------------- (Relacion)
    @OneToOne(mappedBy = "cliente")
    //@JoinColumn(name = "cuenta_id")
    @JsonManagedReference
    private M_Cuenta cuenta;
    //--------------------------------------------------------------------------------------------------------------

    public M_Cliente(String id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public M_Cliente()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
