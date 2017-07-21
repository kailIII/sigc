/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.newvi.sic.servicios.impl;

import ec.com.newvi.sic.dao.AvaluoFacade;
import ec.com.newvi.sic.dao.BloquesFacade;
import ec.com.newvi.sic.dao.FechaAvaluoFacade;
import ec.com.newvi.sic.dao.FotosFacade;
import ec.com.newvi.sic.dao.PisosDetalleFacade;
import ec.com.newvi.sic.dao.PisosFacade;
import ec.com.newvi.sic.dao.PrediosFacade;
import ec.com.newvi.sic.dao.TerrenoFacade;
import ec.com.newvi.sic.dto.AvaluoDto;
import ec.com.newvi.sic.dto.SesionDto;
import ec.com.newvi.sic.enums.EnumEstadoRegistro;
import ec.com.newvi.sic.enums.EnumNewviExcepciones;
import ec.com.newvi.sic.modelo.Avaluo;
import ec.com.newvi.sic.modelo.Bloques;
import ec.com.newvi.sic.modelo.ConstantesImpuestos;
import ec.com.newvi.sic.modelo.FechaAvaluo;
import ec.com.newvi.sic.modelo.Fotos;
import ec.com.newvi.sic.modelo.PisoDetalle;
import ec.com.newvi.sic.modelo.Pisos;
import ec.com.newvi.sic.modelo.Predios;
import ec.com.newvi.sic.modelo.Terreno;
import ec.com.newvi.sic.servicios.CatastroServicio;
import ec.com.newvi.sic.servicios.ParametrosServicio;
import ec.com.newvi.sic.util.ComunUtil;
import ec.com.newvi.sic.util.excepciones.NewviExcepcion;
import ec.com.newvi.sic.util.logs.LoggerNewvi;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Andrés
 */
@Stateless
@PermitAll
public class CatastroServicioImpl implements CatastroServicio {

    @EJB
    PrediosFacade prediosFacade;
    @EJB
    BloquesFacade bloquesFacade;
    @EJB
    PisosFacade pisosFacade;
    @EJB
    TerrenoFacade terrenoFacade;
    @EJB
    FotosFacade fotosFacade;
    @EJB
    PisosDetalleFacade pisosDetalleFacade;
    @EJB
    ParametrosServicio parametrosServicio;
    @EJB
    FechaAvaluoFacade fechaAvaluoFacade;
    @EJB
    AvaluoFacade avaluoFacade;

    /*------------------------------------------------------------Predios------------------------------------------------------------*/
    @Override
    public String generarNuevoPredio(Predios nuevoPredio, SesionDto sesion) throws NewviExcepcion {

        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando predio...", sesion);
        if (!nuevoPredio.esPredioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR338);
        }
        // Crear el predio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando predio...", sesion);

        //Registramos la auditoria de ingreso
        nuevoPredio.setAudIngIp(sesion.getDireccionIP());
        nuevoPredio.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoPredio.setAudIngFec(fechaIngreso);

        prediosFacade.create(nuevoPredio);
        // Si todo marcha bien enviar nombre del predio
        return nuevoPredio.getNomCodigocatastral();

    }

    @Override
    public String actualizarPredio(Predios predio, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando predio...", sesion);
        if (!predio.esPredioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR338);
        }
        // Editar la predio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando predio...", sesion);

        //Registramos la auditoria de modificacion
        predio.setAudModIp(sesion.getDireccionIP());
        predio.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaModificacion = Calendar.getInstance().getTime();
        predio.setAudModFec(fechaModificacion);

        predio.setCodManzana(predio.getCodManzana().trim());

        prediosFacade.edit(predio);
        // Si todo marcha bien enviar nombre de la predio
        return predio.getNomCodigocatastral();
    }

    @Override
    public List<Predios> consultarPredios() {
        return prediosFacade.buscarPredio();
    }

    @Override
    public Predios seleccionarPredio(Integer idPredio) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(idPredio)) {
            return prediosFacade.find(idPredio);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public String eliminarPredio(Predios predio, SesionDto sesion) throws NewviExcepcion {
        predio.setCatEstado(EnumEstadoRegistro.E);
        return actualizarPredio(predio, sesion);
    }

    /*------------------------------------------------------------Bloques------------------------------------------------------------*/
    @Override
    public String generarNuevoBloque(Bloques nuevoBloque, SesionDto sesion) throws NewviExcepcion {

        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando bloque...", sesion);
        if (!nuevoBloque.esBloqueValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR339);
        }
        // Crear el bloque
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando bloque...", sesion);

        //Registramos la auditoria de ingreso
        nuevoBloque.setAudIngIp(sesion.getDireccionIP());
        nuevoBloque.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoBloque.setAudIngFec(fechaIngreso);

        bloquesFacade.create(nuevoBloque);
        // Si todo marcha bien enviar nombre del bloque
        return nuevoBloque.getNomBloque();

    }

    @Override
    public String actualizarBloque(Bloques bloque, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando bloque...", sesion);
        if (!bloque.esBloqueValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR339);
        }
        // Editar la bloque
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando bloque...", sesion);

        //Registramos la auditoria de modificacion
        bloque.setAudModIp(sesion.getDireccionIP());
        bloque.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaModificacion = Calendar.getInstance().getTime();
        bloque.setAudModFec(fechaModificacion);

        bloquesFacade.edit(bloque);
        // Si todo marcha bien enviar nombre de la bloque
        return bloque.getNomBloque();
    }

    @Override
    public List<Bloques> consultarBloques() {
        return bloquesFacade.buscarBloques();
    }

    @Override
    public Bloques seleccionarBloque(Integer idBloque) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(idBloque)) {
            return bloquesFacade.find(idBloque);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public String eliminarBloque(Bloques bloque, SesionDto sesion) throws NewviExcepcion {
        bloque.setBloEstado(EnumEstadoRegistro.E);
        return actualizarBloque(bloque, sesion);
    }

    @Override
    public List<Bloques> buscarBloquesPorCodigoCatastral(Integer codCatastral) {
        return bloquesFacade.buscarBloquesPorCodigoCatastral(codCatastral);
    }

    @Override
    public List<AvaluoDto> obtenerAvaluoPisos(Pisos pisoEnviado, BigDecimal promedioFactores, SesionDto sesion) throws NewviExcepcion {
        BigDecimal areapiso, edad, valorDepreciacion;
        Integer codigo_piso;
        String estado;
        Pisos piso = pisoEnviado;

        estado = "";

        List<AvaluoDto> listaCaracteristicasPisosDto = new ArrayList<>();

        codigo_piso = piso.getCodPisos();

        edad = new BigDecimal(piso.obtenerEdadPiso());
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo("Edad", edad.toString(), null, null));

        estado = piso.getStsEstado();
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo("Estado", estado, null, null));

        areapiso = piso.getValAreapiso();
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo("Area", areapiso.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

        //Factor de depreciación de inmueble por piso y depreciacion
        valorDepreciacion = parametrosServicio.obtenerValorDepreciacion(edad, estado);
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo("Factor Depreciación", valorDepreciacion.toString(), null, null));

        List<AvaluoDto> listaDetallesPiso = obtenerListaDetallesPiso(piso, valorDepreciacion, promedioFactores, sesion);

        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo("Detalles piso", null, null, listaDetallesPiso));
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo("Valoración del piso", null, piso.getValPiso().setScale(2, BigDecimal.ROUND_UP).toString(), null));

        return listaCaracteristicasPisosDto;

    }

    @Override
    public AvaluoDto obtenerAvaluoBloque(Bloques bloque, BigDecimal promedioFactores, SesionDto sesion) throws NewviExcepcion {
        BigDecimal costoBloque;
        BigDecimal areaBloque;
        List<AvaluoDto> listaPisosDto = new ArrayList<>();
        List<AvaluoDto> listaCaracteristicasPisosDto;

        Integer codigo_bloque;

        areaBloque = BigDecimal.ZERO;
        costoBloque = BigDecimal.ZERO;

        codigo_bloque = bloque.getCodBloques();

        for (Pisos piso : bloque.getPisosCollection()) {
            listaCaracteristicasPisosDto = obtenerAvaluoPisos(piso, promedioFactores, sesion);
            listaPisosDto.add(generarElementoArbolAvaluo("Piso: " + piso.getNomPiso(), null, null, listaCaracteristicasPisosDto));
            areaBloque = areaBloque.add(piso.getValAreapiso());
            costoBloque = costoBloque.add(piso.getValPiso());
        }

        // Actualiza Valores por Bloque
        //bloque = seleccionarBloque(codigo_bloque);
        bloque.setValAreabloque(areaBloque);
        bloque.setValBloque(costoBloque);

        actualizarBloque(bloque, sesion);

        return generarElementoArbolAvaluo("Bloque: " + bloque.getNomBloque(), null, null, listaPisosDto);
    }

    /*------------------------------------------------------------Pisos------------------------------------------------------------*/
    @Override
    public String generarNuevoPiso(Pisos nuevoPiso, SesionDto sesion) throws NewviExcepcion {

        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando piso...", sesion);
        if (!nuevoPiso.esPisoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR343);
        }
        // Crear el piso
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando piso...", sesion);

        //Registramos la auditoria de ingreso
        nuevoPiso.setAudIngIp(sesion.getDireccionIP());
        nuevoPiso.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoPiso.setAudIngFec(fechaIngreso);

        pisosFacade.create(nuevoPiso);
        // Si todo marcha bien enviar nombre del piso
        return nuevoPiso.getNomPiso();

    }

    @Override
    public String actualizarPiso(Pisos piso, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando piso...", sesion);
        if (!piso.esPisoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR343);
        }
        // Editar la piso
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando piso...", sesion);

        //Registramos la auditoria de modificacion
        piso.setAudModIp(sesion.getDireccionIP());
        piso.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaModificacion = Calendar.getInstance().getTime();
        piso.setAudModFec(fechaModificacion);

        pisosFacade.edit(piso);
        // Si todo marcha bien enviar nombre de la piso
        return piso.getNomPiso();
    }

    @Override
    public List<Pisos> consultarPisos() {
        return pisosFacade.buscarPisos();
    }

    @Override
    public Pisos seleccionarPiso(Integer idPiso) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(idPiso)) {
            return pisosFacade.find(idPiso);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public String eliminarPiso(Pisos piso, SesionDto sesion) throws NewviExcepcion {
        piso.setPisEstado(EnumEstadoRegistro.E);
        return actualizarPiso(piso, sesion);
    }

    @Override
    public Object[] obtenerDatosPisoPorBloque(Integer codBloque) {
        return pisosFacade.obtenerDatosPisoPorBloque(codBloque);
    }

    @Override
    public Pisos buscarPisosPorCodigoBloque(Integer codBloques) {
        return pisosFacade.buscarPisosPorCodigoBloque(codBloques);
    }

    /*------------------------------------------------------------PisosDetalle------------------------------------------------------------*/
    @Override
    public String actualizarPisoDetalle(PisoDetalle pisoDetalle, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando detalle piso...", sesion);
        if (!pisoDetalle.esDetallePisoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR343);
        }
        // Editar la piso
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando detalle piso...", sesion);

        //Registramos la auditoria de modificacion
        pisoDetalle.setAudModIp(sesion.getDireccionIP());
        pisoDetalle.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaModificacion = Calendar.getInstance().getTime();
        pisoDetalle.setAudModFec(fechaModificacion);

        pisosDetalleFacade.edit(pisoDetalle);
        // Si todo marcha bien enviar nombre de la piso
        return pisoDetalle.getCodigo();
    }

    public List<AvaluoDto> obtenerListaDetallesPiso(Pisos piso, BigDecimal valorDepreciacion, BigDecimal promedioFactores, SesionDto sesion) throws NewviExcepcion {
        List<AvaluoDto> listaDetallesConstruccion = new ArrayList<>();
        BigDecimal coeficienteEstructura, coeficienteAcabado, coeficienteExtras, v1, v2, v3, costoPiso, sumaFactores, depreciacion, areaPiso;
        int codigo_piso = piso.getCodPisos();

        costoPiso = BigDecimal.ZERO;
        areaPiso = piso.getValAreapiso();

        //Detalle de construccion Estructura
        List<AvaluoDto> listaDetallesEstructura = obtenerListaDetallesPisoPorTipo(piso, valorDepreciacion, promedioFactores, "210101", "ESTRUCTURA");
        coeficienteEstructura = parametrosServicio.obtenerCoeficienteConstruccion(piso, "ESTRUCTURA");
        v1 = parametrosServicio.obtenerTotalCoeficienteDominiosPorCodigo("210101");
        listaDetallesConstruccion.add(generarElementoArbolAvaluo("Estructura", null, null, listaDetallesEstructura));

        //Destalle de construccion Acabados
        List<AvaluoDto> listaDetallesAcabado = obtenerListaDetallesPisoPorTipo(piso, valorDepreciacion, promedioFactores, "210102", "ACABADOS");
        coeficienteAcabado = parametrosServicio.obtenerCoeficienteConstruccion(piso, "ACABADOS");
        v2 = parametrosServicio.obtenerTotalCoeficienteDominiosPorCodigo("210102");
        listaDetallesConstruccion.add(generarElementoArbolAvaluo("Acabados", null, null, listaDetallesAcabado));

        //Detalle de construccion Extras
        List<AvaluoDto> listaDetallesExtras = obtenerListaDetallesPisoPorTipo(piso, valorDepreciacion, promedioFactores, "210103", "EXTRAS");
        coeficienteExtras = parametrosServicio.obtenerCoeficienteConstruccion(piso, "EXTRAS");
        v3 = parametrosServicio.obtenerTotalCoeficienteDominiosPorCodigo("210103");
        listaDetallesConstruccion.add(generarElementoArbolAvaluo("Extras", null, null, listaDetallesExtras));

        // Factos de costos del pios es igual a la suma de los factores por el área menos la depreciación del bien por edad y estado
        sumaFactores = areaPiso.multiply((coeficienteEstructura.multiply(v1)).add(coeficienteAcabado.multiply(v2)).add(coeficienteExtras.multiply(v3)));
        depreciacion = sumaFactores.multiply(valorDepreciacion);
        costoPiso = sumaFactores.subtract(depreciacion);

        // Ubica valor de calculos en la tabla de pisos
        //piso = seleccionarPiso(codigo_piso);
        piso.setValFactordepreciacion(valorDepreciacion);
        piso.setValSumafactores(coeficienteEstructura.add(coeficienteAcabado).add(coeficienteExtras));
        piso.setValConstante(promedioFactores);
        piso.setValMetro2(((coeficienteEstructura.multiply(v1)).add(coeficienteAcabado.multiply(v2)).add(coeficienteExtras.multiply(v3))).multiply(valorDepreciacion));
        piso.setValPiso(costoPiso);
        actualizarPiso(piso, sesion);

        return listaDetallesConstruccion;
    }

    public List<AvaluoDto> obtenerListaDetallesPisoPorTipo(Pisos piso, BigDecimal valorDepreciacion, BigDecimal promedioFactores, String codigoDominio, String elementoCalculo) throws NewviExcepcion {
        List<AvaluoDto> listaDetallesPiso = new ArrayList<>();
        BigDecimal coeficiente = parametrosServicio.obtenerCoeficienteConstruccion(piso, elementoCalculo);
        BigDecimal costoMetroReferencial = parametrosServicio.obtenerTotalCoeficienteDominiosPorCodigo(codigoDominio);

        piso.getDetalles().forEach((pisoDetalle) -> {
            AvaluoDto nuevoDetalle = generarNodoDetalle(pisoDetalle, elementoCalculo);
            BigDecimal coeficienteObtenido = new BigDecimal(nuevoDetalle.getFactor());
            if (BigDecimal.ZERO.compareTo(coeficienteObtenido) != 0) {
                listaDetallesPiso.add(nuevoDetalle);
            }
        });

        listaDetallesPiso.add(generarElementoArbolAvaluo("Promedio general " + elementoCalculo.toLowerCase(), null, coeficiente.toString(), null));
        listaDetallesPiso.add(generarElementoArbolAvaluo("Costo metro referencial", null, costoMetroReferencial.toString(), null));

        return listaDetallesPiso;
    }

    /*------------------------------------------------------------Terreno------------------------------------------------------------*/
    @Override
    public String generarNuevoTerreno(Terreno nuevoTerreno, SesionDto sesion) throws NewviExcepcion {

        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando terreno...", sesion);
        if (!nuevoTerreno.esTerrenoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR343);
        }
        // Crear el terreno
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando terreno...", sesion);

        //Registramos la auditoria de ingreso
        nuevoTerreno.setAudIngIp(sesion.getDireccionIP());
        nuevoTerreno.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoTerreno.setAudIngFec(fechaIngreso);

        terrenoFacade.create(nuevoTerreno);
        // Si todo marcha bien enviar nombre del terreno
        return nuevoTerreno.getCodTerrenodetalle().toString();

    }

    @Override
    public String actualizarTerreno(Terreno terreno, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando terreno...", sesion);
        if (!terreno.esTerrenoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR343);
        }
        // Editar la terreno
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando terreno...", sesion);

        //Registramos la auditoria de modificacion
        terreno.setAudModIp(sesion.getDireccionIP());
        terreno.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaModificacion = Calendar.getInstance().getTime();
        terreno.setAudModFec(fechaModificacion);

        terrenoFacade.edit(terreno);
        // Si todo marcha bien enviar nombre de la terreno
        return terreno.getCodTerrenodetalle().toString();
    }

    @Override
    public List<Terreno> consultarTerreno() {
        return terrenoFacade.buscarTerreno();
    }

    @Override
    public Terreno seleccionarTerreno(Integer idTerreno) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(idTerreno)) {
            return terrenoFacade.find(idTerreno);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public String eliminarTerreno(Terreno terreno, SesionDto sesion) throws NewviExcepcion {
        terreno.setTerEstado(EnumEstadoRegistro.E);
        return actualizarTerreno(terreno, sesion);
    }

    /*------------------------------------------------------------Fotos------------------------------------------------------------*/
    @Override
    public List<Fotos> consultarFotosPorPredio(int codCatastral) {
        return fotosFacade.buscarFotosPorPredio(codCatastral);
    }

    @Override
    public List<AvaluoDto> obtenerAvaluoPredio(Predios predio, SesionDto sesion) throws NewviExcepcion {
        BigDecimal coff, cot, cofo, cubi, cero, promedioFactores, div, vterreno, area, frente, valor_terreno, areaConstruccion, valorEdificacion, valPredio, c1, c2, c3, c4, c5, c6, basura, aPagar;
        Integer codigo;
        String zona, sector, consulta;
        Boolean ba;

        codigo = predio.getCodCatastral();
        promedioFactores = BigDecimal.ZERO;
        div = new BigDecimal(5);
        frente = predio.getValAreaFrente();

        List<AvaluoDto> nodo = new ArrayList<>();
        List<AvaluoDto> listaOtrosRubros = new ArrayList<>();

        //if (frente.signum()==0) {
        if (frente.compareTo(BigDecimal.ZERO) == 0) {
            LoggerNewvi.getLogNewvi(this.getClass()).debug("Existen valores negativos no se realizó el calculo del avaluo...", sesion);
            return null;
        } else {
            area = predio.getValAreaPredio();
            areaConstruccion = BigDecimal.ZERO;
            valorEdificacion = BigDecimal.ZERO;

            c1 = BigDecimal.ZERO;
            c2 = BigDecimal.ZERO;
            c3 = BigDecimal.ZERO;
            c4 = BigDecimal.ZERO;
            c5 = BigDecimal.ZERO;
            c6 = BigDecimal.ZERO;

            aPagar = BigDecimal.ZERO;

            zona = predio.getCodZona();
            sector = predio.getCodSector();

            nodo.add(generarElementoArbolAvaluo("Area", area.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            nodo.add(generarElementoArbolAvaluo("Frente", frente.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            nodo.add(generarElementoArbolAvaluo("Fondo relativo", predio.getValAreaFondo().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

            // Calculo del fondo relativo COFF
            coff = obtenerValoracionFondoRelativo(area, frente);
            nodo.add(generarElementoArbolAvaluo("Factor frente fondo", coff.toString(), null, null));

            // Coeficiente de Topografía COT
            cot = parametrosServicio.obtenerCoeficienteTerreno(predio, "TOPOGRAFIA");
            nodo.add(generarElementoArbolAvaluo("Topografía", cot.toString(), null, null));
            // Coeficinte de Erosion
            cero = parametrosServicio.obtenerCoeficienteTerreno(predio, "LOCALIZACION");
            nodo.add(generarElementoArbolAvaluo("Erosión", cero.toString(), null, null));
            // Coeficinte de forma COFO
            cofo = parametrosServicio.obtenerCoeficienteTerreno(predio, "FORMA");
            nodo.add(generarElementoArbolAvaluo("Forma", cofo.toString(), null, null));
            // Coeficinte de Ubicacion
            cubi = parametrosServicio.obtenerCoeficienteTerreno(predio, "OCUPACION");
            nodo.add(generarElementoArbolAvaluo("Ubicación", cubi.toString(), null, null));

            promedioFactores = (promedioFactores.add(coff).add(cot).add(cofo).add(cero).add(cubi)).divide(div, 4, RoundingMode.CEILING);
            nodo.add(generarElementoArbolAvaluo("Promedio de factores", promedioFactores.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

            // CALCULO DEL PRECIO BASE PARA EL TERRENO
            // SE TOMA EN CUENTA UNA VALORACION POR LAS ZONAS y SECTORES DEL MUNICIPIO.
            consulta = "20" + zona + sector;

            vterreno = parametrosServicio.obtenerValorPorCodigoCalculo(consulta, "ZONAS VALORADAS M2");
            nodo.add(generarElementoArbolAvaluo("Precio base en M2 en la zona " + zona + " sector " + sector, vterreno.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

            valor_terreno = (promedioFactores.multiply(area)).multiply(vterreno);

            predio.setValTerreno(valor_terreno);
            actualizarPredio(predio, sesion);

            for (Bloques bloque : predio.getBloques()) {
                nodo.add(obtenerAvaluoBloque(bloque, promedioFactores, sesion));
                valorEdificacion = valorEdificacion.add(bloque.getValBloque());
                areaConstruccion = areaConstruccion.add(bloque.getValAreabloque());
                nodo.add(generarElementoArbolAvaluo("Costo Total bloque", bloque.getValBloque().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            }

            nodo.add(generarElementoArbolAvaluo("Valor del terreno", valor_terreno.setScale(2, BigDecimal.ROUND_UP).toPlainString(), null, null));
            //Actualiza Valoración de Terreno y Contrucción
            predio.setValEdifica(valorEdificacion);
            nodo.add(generarElementoArbolAvaluo("Valor de la edificacion", valorEdificacion.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            predio.setValAreaConstruccion(areaConstruccion);

            valPredio = valor_terreno.add(valorEdificacion);
            nodo.add(generarElementoArbolAvaluo("Valor del predio", valPredio.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

            predio.setValPredio(valPredio);
            actualizarPredio(predio, sesion);

            // Constantes catastro urbano
            List<ConstantesImpuestos> constantesImpuestos = parametrosServicio.obtenerConstantesImpuestosPorTipo("URBANO");

            for (ConstantesImpuestos constantesImpuesto : constantesImpuestos) {
                c1 = constantesImpuesto.getValBomberos();
                c2 = constantesImpuesto.getValServiciosadministrativos();
                c3 = constantesImpuesto.getValCem();
                c4 = constantesImpuesto.getValBasura();
                c5 = constantesImpuesto.getValTasaaplicada();
                c6 = constantesImpuesto.getValAmbientales();
            }

            // Ubica Valor recoleccion de basura segun Zona mirar domi_calculo = TASA RECOLECCIÓN DE BASURA            
            consulta = "60" + zona;
            basura = parametrosServicio.obtenerValorPorCodigoCalculo(consulta, "TASA RECOLECCIÓN DE BASURA");
            ba = parametrosServicio.tieneBasura(codigo);

            if (ba) {
                basura = BigDecimal.ZERO;
            }
            aPagar = ((valPredio.multiply(c5)).add(c2)).add(c3).add(c6).add((valPredio.multiply(c1)).multiply(c5)).add(basura);
            // Actualiza otros valores calculados
            predio.setValCem(c3);
            predio.setValBomberos((valPredio.multiply(c1)).multiply(c5));
            predio.setValEmision(c2);
            predio.setValBasura(basura);
            predio.setValAmbientales(c6);
            predio.setValImpuesto(valPredio.multiply(c5));
            predio.setValImppredial(aPagar);
            actualizarPredio(predio, sesion);

            nodo.add(generarElementoArbolAvaluo("Impuesto predial", predio.getValImpuesto().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

            listaOtrosRubros.add(generarElementoArbolAvaluo("Bomberos", predio.getValBomberos().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            listaOtrosRubros.add(generarElementoArbolAvaluo("Costo emisión", predio.getValEmision().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            listaOtrosRubros.add(generarElementoArbolAvaluo("CEM", predio.getValCem().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            listaOtrosRubros.add(generarElementoArbolAvaluo("Servicios ambientales", predio.getValAmbientales().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
            listaOtrosRubros.add(generarElementoArbolAvaluo("Tasa recolección basura", predio.getValBasura().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

            nodo.add(generarElementoArbolAvaluo("OTROS RUBROS", null, null, listaOtrosRubros));

            nodo.add(generarElementoArbolAvaluo("A pagar", predio.getValImppredial().setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

            //insertarElementosArbolAvaluo("Raiz", null, nodo, this.raiz, null);
            return nodo;
        }
    }

    private BigDecimal obtenerValoracionFondoRelativo(BigDecimal area, BigDecimal frente) {

        BigDecimal v1, v2;
        v1 = area.divide(frente, 4, RoundingMode.CEILING);
        v2 = frente.divide(v1, 4, RoundingMode.CEILING);

        return parametrosServicio.obtenerCOFF(v2, "FACTOR FRENTE FONDO");

    }

    private AvaluoDto generarElementoArbolAvaluo(String descripcion, String valor, String factor, List<AvaluoDto> hijos) {
        AvaluoDto nodoRaiz = new AvaluoDto();

        nodoRaiz.setDescripcion(descripcion.trim());
        nodoRaiz.setValor(valor);
        nodoRaiz.setHijos(hijos);
        nodoRaiz.setFactor(factor);
        //nodoRaiz.setValor2("prueba");
        return nodoRaiz;
    }

    private AvaluoDto generarNodoDetalle(PisoDetalle pisoDetalle, String elementoCalculo) {
        BigDecimal coeficiente = parametrosServicio.obtenerCoeficienteDetallePiso(pisoDetalle, elementoCalculo);
        return generarElementoArbolAvaluo(pisoDetalle.getSubgrupo(), pisoDetalle.getDescripcion(), coeficiente.toString(), null);
    }

    @Override
    public FechaAvaluo generarNuevaFechaAvaluo(FechaAvaluo nuevoFechaAvaluo, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando fecha avaluo...", sesion);
        if (!nuevoFechaAvaluo.esFechaAvaluoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR363);
        }
        // Crear el fecha avaluo
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando fecha avaluo...", sesion);

        //Registramos la auditoria de ingreso
        nuevoFechaAvaluo.setAudIngIp(sesion.getDireccionIP());
        nuevoFechaAvaluo.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoFechaAvaluo.setAudIngFec(fechaIngreso);

        fechaAvaluoFacade.create(nuevoFechaAvaluo);

        // Si todo marcha bien enviar id de la fecha avaluo
        return nuevoFechaAvaluo;
    }

    @Override
    public Integer generarNuevoAvaluo(Avaluo nuevoAvaluo, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando fecha avaluo...", sesion);
        if (!nuevoAvaluo.esAvaluoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR362);
        }
        // Crear el avaluo
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando fecha avaluo...", sesion);

        //Registramos la auditoria de ingreso
        nuevoAvaluo.setAudIngIp(sesion.getDireccionIP());
        nuevoAvaluo.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoAvaluo.setAudIngFec(fechaIngreso);

        avaluoFacade.create(nuevoAvaluo);

        // Si todo marcha bien enviar id de avaluo
        return nuevoAvaluo.getAvalId();
    }
    
    @Override
    public Avaluo seleccionarAvaluo(Integer avalId) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(avalId)) {
            return avaluoFacade.find(avalId);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public List<Avaluo> consultarAvaluos(Date fecavFechaavaluo) {
        return avaluoFacade.buscarAvaluos(fecavFechaavaluo);
    }
    @Override
    public List<FechaAvaluo> consultarFechaAvaluos(){
        return fechaAvaluoFacade.buscarFechaAvaluos();
    }
    
    @Override
    public List<Avaluo> consultarListaAvaluosActuales(){
        Avaluo avaluoActual = avaluoFacade.consultarAvaluoActual();
        return avaluoFacade.consultarAvaluos(avaluoActual.getFecavId().getFecavFechaavaluo());
    }
    @Override
    public List<Avaluo> consultarListaAvaluosPorFecha(Date fechaAvaluo){
        return avaluoFacade.consultarAvaluos(fechaAvaluo);
    }

}
