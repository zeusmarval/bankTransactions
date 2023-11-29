package EPA.Cuenta_Bancaria_Web;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Repositorio.I_Repositorio_Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CuentaBancariaWebApplication {

	//@Autowired
	//I_Repositorio_Cuenta cuenta;

	public static void main(String[] args)
	{
		SpringApplication.run(CuentaBancariaWebApplication.class, args);
	}

	/*
	@Override
	public void run(String... args) throws Exception
	{

		List<M_Cuenta> list = new ArrayList<>();
		list = cuenta.findAll();

		for (M_Cuenta t : list)
		{
			System.out.println(t.toString());
		}

		M_Cuenta c = cuenta.findById(1);

	}

	 */
}
