package EPA.Cuenta_Bancaria_Web.Controlador;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.Enum_Tipos_Deposito;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Transaccion;
import EPA.Cuenta_Bancaria_Web.Servicio.Transaccion.I_Transaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("Transacciones")
public class C_Transaccion
{
    @Autowired
    @Qualifier("MONGO")
    I_Transaccion transaccion;

    @GetMapping(value = "/listar_transacciones")
    public ResponseEntity<List<M_Transaccion_DTO>> listar_transacciones()
    {
        List<M_Transaccion_DTO> l_Transacciones = transaccion.findAll();
        return ResponseEntity.ok(l_Transacciones);
    }

    @PostMapping(value = "/Crear/Deposito/Cajero/{id_Cuenta}/{monto}")
    public ResponseEntity<?> Procesar_Deposito_Cajero(@PathVariable String id_Cuenta,
                                                      @PathVariable BigDecimal monto)
    {
        M_Transaccion_DTO transaccion_Creada = transaccion.Procesar_Deposito(id_Cuenta, Enum_Tipos_Deposito.CAJERO, monto);

        return ResponseEntity.ok(transaccion_Creada);
    }

    @PostMapping(value = "/Crear/Deposito/Sucursal/{id_Cuenta}/{monto}")
    public ResponseEntity<?> Procesar_Deposito_Sucursal(@PathVariable String id_Cuenta,
                                                        @PathVariable BigDecimal monto)
    {
        M_Transaccion_DTO transaccion_Creada = transaccion.Procesar_Deposito(id_Cuenta, Enum_Tipos_Deposito.SUCURSAL, monto);

        return ResponseEntity.ok(transaccion_Creada);
    }

    @PostMapping(value = "/Crear/Deposito/OtraCuenta/{id_Cuenta}/{monto}")
    public ResponseEntity<?> Procesar_Deposito_OtraCuenta(@PathVariable String id_Cuenta,
                                                          @PathVariable BigDecimal monto)
    {
        M_Transaccion_DTO transaccion_Creada = transaccion.Procesar_Deposito(id_Cuenta, Enum_Tipos_Deposito.OTRA_CUENTA, monto);

        return ResponseEntity.ok(transaccion_Creada);
    }


}
