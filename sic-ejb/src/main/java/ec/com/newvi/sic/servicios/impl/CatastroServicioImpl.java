/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.newvi.sic.servicios.impl;

import ec.com.newvi.sic.dao.AvaluoFacade;
import ec.com.newvi.sic.dao.BloquesFacade;
import ec.com.newvi.sic.dao.ContribucionMejorasFacade;
import ec.com.newvi.sic.dao.DetallesAvaluoFacade;
import ec.com.newvi.sic.dao.FechaAvaluoFacade;
import ec.com.newvi.sic.dao.FotosFacade;
import ec.com.newvi.sic.dao.LogPredioFacade;
import ec.com.newvi.sic.dao.PisosDetalleFacade;
import ec.com.newvi.sic.dao.PisosFacade;
import ec.com.newvi.sic.dao.PrediosFacade;
import ec.com.newvi.sic.dao.ServicioFacade;
import ec.com.newvi.sic.dao.TerrenoFacade;
import ec.com.newvi.sic.dto.AvaluoDto;
import ec.com.newvi.sic.dto.FichaCatastralDto;
import ec.com.newvi.sic.dto.SesionDto;
import ec.com.newvi.sic.enums.EnumCaracteristicasAvaluo;
import ec.com.newvi.sic.enums.EnumEstadoRegistro;
import ec.com.newvi.sic.enums.EnumNewviExcepciones;
import ec.com.newvi.sic.enums.EnumParametroSistema;
import ec.com.newvi.sic.enums.EnumSiNo;
import ec.com.newvi.sic.enums.EnumZonaInfluencia;
import ec.com.newvi.sic.modelo.Avaluo;
import ec.com.newvi.sic.modelo.Bloques;
import ec.com.newvi.sic.modelo.ConstantesImpuestos;
import ec.com.newvi.sic.modelo.ContribucionMejoras;
import ec.com.newvi.sic.modelo.DetallesAvaluo;
import ec.com.newvi.sic.modelo.Dominios;
import ec.com.newvi.sic.modelo.FechaAvaluo;
import ec.com.newvi.sic.modelo.Fotos;
import ec.com.newvi.sic.modelo.LogPredio;
import ec.com.newvi.sic.modelo.PisoDetalle;
import ec.com.newvi.sic.modelo.Pisos;
import ec.com.newvi.sic.modelo.Predios;
import ec.com.newvi.sic.modelo.Servicios;
import ec.com.newvi.sic.modelo.Tenencia;
import ec.com.newvi.sic.modelo.Terreno;
import ec.com.newvi.sic.servicios.CatastroServicio;
import ec.com.newvi.sic.servicios.ParametrosServicio;
import ec.com.newvi.sic.util.ComunUtil;
import ec.com.newvi.sic.util.excepciones.NewviExcepcion;
import ec.com.newvi.sic.util.logs.LoggerNewvi;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @EJB
    DetallesAvaluoFacade detallesAvaluoFacade;
    @EJB
    LogPredioFacade logPredioFacade;
    @EJB
    ServicioFacade servicioFacade;
    @EJB
    ContribucionMejorasFacade cemFacade;


    /*------------------------------------------------------------Predios------------------------------------------------------------*/
    @Override
    public Integer generarNuevoPredio(Predios nuevoPredio, SesionDto sesion) throws NewviExcepcion {

        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando predio...", sesion);
        if (!nuevoPredio.esPredioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR338);
        }
        // Crear el predio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando predio...", sesion);

        //Registramos la auditoria de ingreso
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoPredio.setAudIngIp(sesion.getDireccionIP());
        nuevoPredio.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        nuevoPredio.setAudIngFec(fechaIngreso);

        prediosFacade.create(nuevoPredio);
        // Si todo marcha bien enviar nombre del predio
        return nuevoPredio.getCodCatastral();

    }

    @Override
    public Integer actualizarPredio(Predios predio, SesionDto sesion) throws NewviExcepcion {
        //generarLogPredio(predio);
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando predio...", sesion);
        if (!predio.esPredioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR338);
        }
        // Editar la predio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando predio...", sesion);

        //Registramos la auditoria de modificacion
        Date fechaModificacion = Calendar.getInstance().getTime();
        predio.setAudModIp(sesion.getDireccionIP());
        predio.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        predio.setAudModFec(fechaModificacion);

        //predio.setCodManzana(predio.getCodManzana().trim());
        prediosFacade.edit(predio);

        // Si todo marcha bien enviar nombre de la predio
        return predio.getCodCatastral();
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
        actualizarPredio(predio, sesion);
        return predio.getNomCodigocatastral();
    }

    public Integer obtenerNumeroPredios() {
        return prediosFacade.obtenerNumeroPredios();
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
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoBloque.setAudIngIp(sesion.getDireccionIP());
        nuevoBloque.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        nuevoBloque.setAudIngFec(fechaIngreso);

        bloquesFacade.create(nuevoBloque);
        // Si todo marcha bien enviar nombre del bloque
        return nuevoBloque.getCodBloques().toString();

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
        Date fechaModificacion = Calendar.getInstance().getTime();
        bloque.setAudModIp(sesion.getDireccionIP());
        bloque.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
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
    public BigDecimal obtenerValorElementoAvaluoPorDescripcion(List<AvaluoDto> listaElementos, String descripcion, String formatoMonedaSistema) throws NewviExcepcion {
        AvaluoDto elementoEncontrado = obtenerElementoAvaluoPorDescripcion(listaElementos, descripcion);
        BigDecimal valorElemento = BigDecimal.ZERO;
        if (!ComunUtil.esNulo(elementoEncontrado)) {
            valorElemento = !ComunUtil.esNulo(elementoEncontrado.getValor()) ? ComunUtil.obtenerNumeroDecimalDesdeTexto(elementoEncontrado.getValor(), formatoMonedaSistema) : BigDecimal.ZERO;
        }
        return valorElemento;
    }

    @Override
    public AvaluoDto obtenerElementoAvaluoPorDescripcion(List<AvaluoDto> listaElementos, String descripcion) throws NewviExcepcion {
        for (AvaluoDto elemento : listaElementos) {
            if (elemento.getDescripcion().contains(descripcion)) {
                return elemento;
            }
        }
        return null;
    }

    public List<Dominios> buscarDominiosPorEstadoReparacion(List<Dominios> dominios, String domiDescripcion, String domiCalculo) {
        List<Dominios> listaDominios = new ArrayList<>();
        for (Dominios nuevoDominio : dominios) {
            if (nuevoDominio.getDomiDescripcion().contains(domiDescripcion.trim())
                    && nuevoDominio.getDomiCalculo().contains(domiCalculo.trim())) {
                listaDominios.add(nuevoDominio);
            }
        }
        return listaDominios;
    }

    public BigDecimal obtenerValorDepreciacion(BigDecimal dominio, String domiDescripcion, List<Dominios> dominios) {
        //for (Dominios objetoDominio : dominiosFacade.buscarDominiosPorEstadoReparacion(domiDescripcion)) {
        for (Dominios objetoDominio : buscarDominiosPorEstadoReparacion(dominios, domiDescripcion, "ESTADO DE REPARACION")) {
            if ((BigDecimal.valueOf(objetoDominio.getDomiMinimo()).compareTo(dominio) <= 0)
                    && (BigDecimal.valueOf(objetoDominio.getDomiMaximo()).compareTo(dominio) >= 0)) {
                return BigDecimal.valueOf(objetoDominio.getDomiCoefic());
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<AvaluoDto> obtenerAvaluoPisos(Pisos pisoEnviado, BigDecimal promedioFactores, List<Dominios> dominios, String formatoMonedaSistema, SesionDto sesion) throws NewviExcepcion {
        Pisos piso = pisoEnviado;
        BigDecimal areapiso = !ComunUtil.esNulo(piso.getValAreapiso()) ? piso.getValAreapiso() : BigDecimal.ZERO;
        BigDecimal edad = new BigDecimal(piso.obtenerEdadPiso());
        BigDecimal valorDepreciacion;
        String estado = piso.getStsEstado();
        List<AvaluoDto> listaCaracteristicasPisosDto = new ArrayList<>();
        //Factor de depreciación de inmueble por piso y depreciacion
        //valorDepreciacion = parametrosServicio.obtenerValorDepreciacion(edad, estado);
        valorDepreciacion = obtenerValorDepreciacion(edad, estado, dominios);
        List<AvaluoDto> listaDetallesPiso = obtenerListaDetallesPiso(piso, valorDepreciacion, promedioFactores, dominios, formatoMonedaSistema, sesion);
        BigDecimal valorPiso = obtenerValorElementoAvaluoPorDescripcion(listaDetallesPiso, EnumCaracteristicasAvaluo.DETALLE_VALORACION.getTitulo(), formatoMonedaSistema);
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PISO_CODIGO.getTitulo(), piso.getCodPisos().toString(), null, null));
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PISO_EDAD.getTitulo(), edad.toString(), null, null));
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PISO_ESTADO.getTitulo(), estado, null, null));
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PISO_AREA.getTitulo(), areapiso.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PISO_FACTOR_DEPRECIACION.getTitulo(), valorDepreciacion.toString(), null, null));
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PISO_DETALLE.getTitulo(), null, null, listaDetallesPiso));
        listaCaracteristicasPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PISO_VALORACION.getTitulo(), ComunUtil.generarFormatoMoneda(valorPiso, formatoMonedaSistema), null, null));
        return listaCaracteristicasPisosDto;

    }

    @Override
    public List<AvaluoDto> obtenerAvaluoBloque(Bloques bloque, BigDecimal promedioFactores, List<Dominios> dominios, String formatoMonedaSistema, SesionDto sesion) throws NewviExcepcion {
        BigDecimal costoBloque = BigDecimal.ZERO;
        BigDecimal areaBloque = BigDecimal.ZERO;
        List<AvaluoDto> listaPisosDto = new ArrayList<>();
        List<AvaluoDto> listaCaracteristicasPisosDto;
        for (Pisos piso : bloque.getPisosActivos()) {
            if (piso.getPisEstado().equals(EnumEstadoRegistro.A)) {
                listaCaracteristicasPisosDto = obtenerAvaluoPisos(piso, promedioFactores, dominios, formatoMonedaSistema, sesion);
                areaBloque = areaBloque.add(obtenerValorElementoAvaluoPorDescripcion(listaCaracteristicasPisosDto, EnumCaracteristicasAvaluo.PISO_AREA.getTitulo(), formatoMonedaSistema));
                costoBloque = costoBloque.add(obtenerValorElementoAvaluoPorDescripcion(listaCaracteristicasPisosDto, EnumCaracteristicasAvaluo.PISO_VALORACION.getTitulo(), formatoMonedaSistema));
                listaPisosDto.add(generarElementoArbolAvaluo("Piso: " + piso.getNomPiso(), null, null, listaCaracteristicasPisosDto));
            }
        }
        listaPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.BLOQUE_VALORACION.getTitulo(), ComunUtil.generarFormatoMoneda(costoBloque, formatoMonedaSistema), null, null));
        listaPisosDto.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.BLOQUE_AREA.getTitulo(), areaBloque.toString(), null, null));
        // Actualiza Valores por Bloque
        // TODO guardar valores al final
        /*bloque = seleccionarBloque(codigo_bloque);
        bloque.setValAreabloque(areaBloque);
        bloque.setValBloque(costoBloque);
        actualizarBloque(bloque, sesion);*/

        return listaPisosDto;
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
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoPiso.setAudIngIp(sesion.getDireccionIP());
        nuevoPiso.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        nuevoPiso.setAudIngFec(fechaIngreso);

        pisosFacade.create(nuevoPiso);
        // Si todo marcha bien enviar nombre del piso
        return nuevoPiso.getCodPisos().toString();

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
        Date fechaModificacion = Calendar.getInstance().getTime();
        piso.setAudModIp(sesion.getDireccionIP());
        piso.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
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

    @Override
    public List<Pisos> consultarStsEstadoPiso() {
        return pisosFacade.buscarStsEstadoPisos();
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
        Date fechaModificacion = Calendar.getInstance().getTime();
        pisoDetalle.setAudModIp(sesion.getDireccionIP());
        pisoDetalle.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        pisoDetalle.setAudModFec(fechaModificacion);

        pisosDetalleFacade.edit(pisoDetalle);
        // Si todo marcha bien enviar nombre de la piso
        return pisoDetalle.getCodigo();
    }

    @Override
    public String generarNuevoPisoDetalle(PisoDetalle nuevoPisoDetalle, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando detalle piso...", sesion);
        if (!nuevoPisoDetalle.esDetallePisoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR343);
        }
        // Crear el piso
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando detalle piso...", sesion);

        //Registramos la auditoria de ingreso
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoPisoDetalle.setAudIngIp(sesion.getDireccionIP());
        nuevoPisoDetalle.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        nuevoPisoDetalle.setAudIngFec(fechaIngreso);

        pisosDetalleFacade.create(nuevoPisoDetalle);
        // Si todo marcha bien enviar nombre del piso
        return nuevoPisoDetalle.getCodPisoDetalle().toString();
    }

    private BigDecimal obtenerTotalCoeficienteDominiosPorCodigo(String codigo, List<Dominios> dominios) {
        BigDecimal totalCoeficiente = BigDecimal.ZERO;
        for (Dominios dominio : dominios) {
            if (dominio.getDomiCodigo().contains(codigo)) {
                totalCoeficiente = totalCoeficiente.add(BigDecimal.valueOf(dominio.getDomiCoefic()));
            }
        }
        return totalCoeficiente;
    }

    private BigDecimal obtenerCoeficienteConstruccion(Pisos piso, String domiCalculo, List<Dominios> dominios) {
        BigDecimal totalDetalleConstruccion = BigDecimal.ZERO;
        BigDecimal promedioCoeficientes = BigDecimal.ZERO;
        Integer totalCoeficientesConstruccion = 0;
        for (PisoDetalle detalle : piso.getDetallesPisosActivos()) {
            if (detalle.getEstado().equals(EnumEstadoRegistro.A)) {
                totalDetalleConstruccion = totalDetalleConstruccion.add(obtenerCoeficienteDetallePiso(detalle, domiCalculo, dominios));
                totalCoeficientesConstruccion++;
            }
        }
        if (totalCoeficientesConstruccion.compareTo(0) != 0) {
            promedioCoeficientes = totalDetalleConstruccion.divide(new BigDecimal(totalCoeficientesConstruccion), 12, RoundingMode.CEILING);
        }
        return promedioCoeficientes;
    }

    private BigDecimal obtenerCoeficienteDetallePiso(PisoDetalle detalle, String domiCalculo, List<Dominios> dominios) {
        BigDecimal totalDetalle = BigDecimal.ZERO;
        for (Dominios dominio : dominios) {
            if (!ComunUtil.esNulo(dominio.getDomiCodigo()) && dominio.getDomiCodigo().contains(!ComunUtil.esNulo(detalle.getCodigo()) ? detalle.getCodigo().trim() : "")
                    && !ComunUtil.esNulo(dominio.getDomiGrupos()) && dominio.getDomiGrupos().contains("DESCRIPCION EDIFICACION")
                    && !ComunUtil.esNulo(dominio.getDomiCalculo()) && dominio.getDomiCalculo().contains(domiCalculo)) {
                totalDetalle = totalDetalle.add(BigDecimal.valueOf(dominio.getDomiCoefic()));
            }
        }
        return totalDetalle;
    }

    public List<AvaluoDto> obtenerListaDetallesPiso(Pisos piso, BigDecimal valorDepreciacion, BigDecimal promedioFactores, List<Dominios> dominios, String formatoMonedaSistema, SesionDto sesion) throws NewviExcepcion {

        List<AvaluoDto> listaDetallesConstruccion = new ArrayList<>();
        List<AvaluoDto> listaDetallesEstructura = obtenerListaDetallesPisoPorTipo(piso, valorDepreciacion, promedioFactores, "210101", "ESTRUCTURA", dominios);
        List<AvaluoDto> listaDetallesAcabado = obtenerListaDetallesPisoPorTipo(piso, valorDepreciacion, promedioFactores, "210102", "ACABADOS", dominios);
        List<AvaluoDto> listaDetallesExtras = obtenerListaDetallesPisoPorTipo(piso, valorDepreciacion, promedioFactores, "210103", "EXTRAS", dominios);
        BigDecimal coeficienteEstructura = obtenerCoeficienteConstruccion(piso, "ESTRUCTURA", dominios);
        BigDecimal coeficienteAcabado = obtenerCoeficienteConstruccion(piso, "ACABADOS", dominios);
        BigDecimal coeficienteExtras = obtenerCoeficienteConstruccion(piso, "EXTRAS", dominios);
        BigDecimal v1 = obtenerValorElementoAvaluoPorDescripcion(listaDetallesEstructura, EnumCaracteristicasAvaluo.DETALLE_COSTO_METRO_REFERENCIAL.getTitulo(), formatoMonedaSistema);
        BigDecimal v2 = obtenerValorElementoAvaluoPorDescripcion(listaDetallesAcabado, EnumCaracteristicasAvaluo.DETALLE_COSTO_METRO_REFERENCIAL.getTitulo(), formatoMonedaSistema);
        BigDecimal v3 = obtenerValorElementoAvaluoPorDescripcion(listaDetallesExtras, EnumCaracteristicasAvaluo.DETALLE_COSTO_METRO_REFERENCIAL.getTitulo(), formatoMonedaSistema);
        BigDecimal areaPiso = !ComunUtil.esNulo(piso.getValAreapiso()) ? piso.getValAreapiso() : BigDecimal.ZERO;

        // Factos de costos del pios es igual a la suma de los factores por el área menos la depreciación del bien por edad y estado
        BigDecimal sumaFactores = areaPiso.multiply((coeficienteEstructura.multiply(v1)).add(coeficienteAcabado.multiply(v2)).add(coeficienteExtras.multiply(v3)));
        BigDecimal depreciacion = sumaFactores.multiply(valorDepreciacion);
        //BigDecimal costoPiso = sumaFactores.subtract(depreciacion);
        BigDecimal costoPiso = depreciacion;

        listaDetallesConstruccion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.DETALLE_ESTRUCTURA.getTitulo(), null, null, listaDetallesEstructura));
        listaDetallesConstruccion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.DETALLE_ACABADOS.getTitulo(), null, null, listaDetallesAcabado));
        listaDetallesConstruccion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.DETALLE_EXTRAS.getTitulo(), null, null, listaDetallesExtras));
        listaDetallesConstruccion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.DETALLE_VALORACION_METRO.getTitulo(), (((coeficienteEstructura.multiply(v1)).add(coeficienteAcabado.multiply(v2)).add(coeficienteExtras.multiply(v3))).multiply(valorDepreciacion)).setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
        listaDetallesConstruccion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.DETALLE_VALORACION.getTitulo(), costoPiso.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
        listaDetallesConstruccion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.DETALLE_FACTORES.getTitulo(), (coeficienteEstructura.add(coeficienteAcabado).add(coeficienteExtras)).setScale(2, BigDecimal.ROUND_UP).toString(), null, null));

        return listaDetallesConstruccion;
    }

    public List<AvaluoDto> obtenerListaDetallesPisoPorTipo(Pisos piso, BigDecimal valorDepreciacion, BigDecimal promedioFactores, String codigoDominio, String elementoCalculo, List<Dominios> dominios) throws NewviExcepcion {
        List<AvaluoDto> listaDetallesPiso = new ArrayList<>();
        BigDecimal coeficiente = obtenerCoeficienteConstruccion(piso, elementoCalculo, dominios);
        BigDecimal costoMetroReferencial = obtenerTotalCoeficienteDominiosPorCodigo(codigoDominio, dominios);

        piso.getDetallesPisosActivos().forEach((pisoDetalle) -> {
            if (pisoDetalle.getEstado().equals(EnumEstadoRegistro.A)) {
                AvaluoDto nuevoDetalle = generarNodoDetalle(pisoDetalle, elementoCalculo, dominios);
                BigDecimal coeficienteObtenido = new BigDecimal(nuevoDetalle.getFactor());
                if (BigDecimal.ZERO.compareTo(coeficienteObtenido) != 0) {
                    listaDetallesPiso.add(nuevoDetalle);
                }
            }
        });

        listaDetallesPiso.add(generarElementoArbolAvaluo("Promedio general " + elementoCalculo.toLowerCase(), null, coeficiente.setScale(2, BigDecimal.ROUND_UP).toString(), null));
        listaDetallesPiso.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.DETALLE_COSTO_METRO_REFERENCIAL.getTitulo(), costoMetroReferencial.toString(), null, null));

        return listaDetallesPiso;
    }

    @Override
    public PisoDetalle seleccionarDetallePiso(Integer codDetallePiso) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(codDetallePiso)) {
            return pisosDetalleFacade.find(codDetallePiso);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public List<PisoDetalle> consultarStsEstadoDetallePiso() {
        return pisosDetalleFacade.buscarStsEstadoDetallePisos();
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
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoTerreno.setAudIngIp(sesion.getDireccionIP());
        nuevoTerreno.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
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
        Date fechaModificacion = Calendar.getInstance().getTime();
        terreno.setAudModIp(sesion.getDireccionIP());
        terreno.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
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

    public BigDecimal obtenerM2Ame(List<Dominios> dominios, String codigo, String codPredio, String calculo) {
        for (Dominios dominioObtenido : obtenerDominiosPorCalculoYManzana(dominios, calculo, codigo)) {
            String codPredioObtenido = dominioObtenido.getDomiCodigo().trim().substring(8, 11);
            if (codPredioObtenido.equals(codPredio)) {
                return new BigDecimal(dominioObtenido.getDomiCoefic());
            }
        }
        return null;
    }

    public List<Dominios> obtenerDominiosPorCalculoYManzana(List<Dominios> dominios, String calculo, String codigo) {
        List<Dominios> dominiosFiltrados = new ArrayList<>();
        for (Dominios dominioObtenido : dominios) {
            //String codDominio = dominioObtenido.getDomiCodigo().trim().substring(0, 8);
            String codDominio = dominioObtenido.getDomiCodigo().trim().length() > 9 ? dominioObtenido.getDomiCodigo().trim().substring(0, 8) : "0";
            if (!ComunUtil.esNulo(dominioObtenido.getDomiCalculo())
                    && (dominioObtenido.getDomiCalculo().trim()).contains(calculo) && codDominio.equals(codigo)) {
                dominiosFiltrados.add(dominioObtenido);
            }
        }
        return dominiosFiltrados;
    }

    public List<Dominios> obtenerDominiosPorGrupo(List<Dominios> dominios, String grupo) {
        List<Dominios> dominiosFiltrados = new ArrayList<>();
        for (Dominios dominioObtenido : dominios) {
            if (!ComunUtil.esNulo(dominioObtenido.getDomiGrupos())
                    && (dominioObtenido.getDomiGrupos().trim()).contains(grupo)) {
                dominiosFiltrados.add(dominioObtenido);
            }
        }
        return dominiosFiltrados;
    }

    public BigDecimal obtenerSumaCoeficientes(List<Dominios> dominios) {
        BigDecimal coeficiente = new BigDecimal(BigInteger.ZERO);
        for (Dominios dominio : dominios) {
            coeficiente = coeficiente.add(BigDecimal.valueOf(dominio.getDomiCoefic()));
        }
        return coeficiente;
    }

    public List<Dominios> obtenerDominiosPorCodigoYCalculo(List<Dominios> dominios, String codigo, String calculo) {
        List<Dominios> dominiosFiltrados = new ArrayList<>();
        for (Dominios dominioObtenido : dominios) {
            String codigoDominio = !ComunUtil.esNulo(dominioObtenido.getDomiCodigo()) ? dominioObtenido.getDomiCodigo().trim() : "";
            String calculoDominio = !ComunUtil.esNulo(dominioObtenido.getDomiCalculo()) ? dominioObtenido.getDomiCalculo().trim() : "";
            if ((codigoDominio).contains(codigo)
                    && (calculoDominio).contains(calculo)) {
                dominiosFiltrados.add(dominioObtenido);
            }
        }
        return dominiosFiltrados;
    }

    public BigDecimal obtenerCoeficienteTerreno(Predios predio, List<Dominios> dominios, String domiCalculo) {
        List<Dominios> listaDominiosTerreno;
        BigDecimal totalCoeficienteCalculo = BigDecimal.ZERO;
        BigDecimal promedioCoeficientes = BigDecimal.ZERO;
        Integer totalCoeficientesTerreno = 0;
        for (Terreno terreno : predio.getCaracteristicasTerrenoActivas()) {
            listaDominiosTerreno = obtenerDominiosPorCodigoYCalculo(dominios, !ComunUtil.esNulo(terreno.getStsCodigo()) ? terreno.getStsCodigo().trim() : "", domiCalculo);
            for (Dominios dominio : listaDominiosTerreno) {
                totalCoeficienteCalculo = totalCoeficienteCalculo.add(BigDecimal.valueOf(dominio.getDomiCoefic()));
                totalCoeficientesTerreno++;
            }
        }
        if (totalCoeficientesTerreno.compareTo(0) != 0) {
            promedioCoeficientes = totalCoeficienteCalculo.divide(new BigDecimal(totalCoeficientesTerreno), 12, RoundingMode.CEILING);
        }
        return promedioCoeficientes;
    }

    public BigDecimal obtenerCoeficienteServicio(Predios predio, List<Dominios> dominios, String domiCalculo) {
        List<Dominios> listaDominiosServicio;
        BigDecimal totalCoeficienteCalculo = BigDecimal.ZERO;
        BigDecimal promedioCoeficientes = BigDecimal.ZERO;
        Integer totalCoeficientesServicios = 0;
        for (Servicios servicio : predio.getServicosActivos()) {
            listaDominiosServicio = obtenerDominiosPorCodigoYCalculo(dominios, !ComunUtil.esNulo(servicio.getStsCodigo()) ? servicio.getStsCodigo().trim() : "", domiCalculo);
            for (Dominios dominio : listaDominiosServicio) {
                totalCoeficienteCalculo = totalCoeficienteCalculo.add(BigDecimal.valueOf(dominio.getDomiCoefic()));
                totalCoeficientesServicios++;
            }
        }
        if (totalCoeficientesServicios.compareTo(0) != 0) {
            promedioCoeficientes = totalCoeficienteCalculo.divide(new BigDecimal(totalCoeficientesServicios), 12, RoundingMode.CEILING);
        }
        return promedioCoeficientes;
    }

    public BigDecimal obtenerValorPorCodigoCalculo(List<Dominios> dominios, String codigo, String calculo) {
        List<Dominios> listaDominios = obtenerDominiosPorCodigoYCalculo(dominios, codigo, calculo);
        return obtenerSumaCoeficientes(listaDominios);
    }

    public String quitarDecimales(BigDecimal valor) {
        String valorARedondear = valor.setScale(2, BigDecimal.ROUND_UP).toString();
        if ((valorARedondear.substring(valorARedondear.indexOf("."), valorARedondear.length())).length() == 2) {
            valorARedondear = valorARedondear.concat("00");
        }

        return valorARedondear;
    }

    @Override
    public List<AvaluoDto> obtenerAvaluoPredio(Predios predio, List<Dominios> dominios, String formatoMonedaSistema, SesionDto sesion) throws NewviExcepcion {

        List<AvaluoDto> listaAvaluoPredio = new ArrayList<>();
        BigDecimal valorTerreno, valPredio;
        BigDecimal promedioFactores;
        BigDecimal areaConstruccion;
        BigDecimal valorEdificacion;

        // Obtener Coeficientes de Terreno
        List<AvaluoDto> coeficientesTerreno = generarCoeficientesTerreno(predio, dominios);
        // Obtener Coeficientes de Servicios
        List<AvaluoDto> coeficientesServicios = generarCoeficientesServicios(predio, dominios);
        // Obtener Promedio de Coeficientes
        List<AvaluoDto> listaCoeficientes = new ArrayList<>();
        listaCoeficientes.addAll(coeficientesTerreno);
        listaCoeficientes.addAll(coeficientesServicios);
        promedioFactores = obtenerPromedioCoeficientes(listaCoeficientes, formatoMonedaSistema);
        // Obtener Precio Terreno
        List<AvaluoDto> listaPrecioTerreno = obtenerValorTerreno(predio, dominios, listaCoeficientes, promedioFactores, formatoMonedaSistema);
        // Obtener Precio Edificacion
        List<AvaluoDto> listaPrecioEdificacion = obtenerValorEdificacion(predio, dominios, promedioFactores, formatoMonedaSistema, sesion);

        listaAvaluoPredio.addAll(generarListadoInfoTerreno(predio));
        //listaAvaluoPredio.addAll(coeficientesTerreno);
        //listaAvaluoPredio.addAll(coeficientesServicios);
        listaAvaluoPredio.addAll(listaPrecioTerreno);
        listaAvaluoPredio.addAll(listaPrecioEdificacion);

        valorTerreno = obtenerValorElementoAvaluoPorDescripcion(listaPrecioTerreno, EnumCaracteristicasAvaluo.PREDIO_VALOR_TERRENO.getTitulo(), formatoMonedaSistema);
        areaConstruccion = obtenerValorElementoAvaluoPorDescripcion(listaPrecioEdificacion, EnumCaracteristicasAvaluo.PREDIO_TERRENO_AREA_CONSTRUCCION.getTitulo(), formatoMonedaSistema);
        valorEdificacion = obtenerValorElementoAvaluoPorDescripcion(listaPrecioEdificacion, EnumCaracteristicasAvaluo.PREDIO_VALOR_EDIFICACION.getTitulo(), formatoMonedaSistema);
        valPredio = valorTerreno.add(valorEdificacion);

        predio.setValTerreno(valorTerreno);
        predio.setValEdifica(valorEdificacion);
        predio.setValAreaConstruccion(areaConstruccion);
        predio.setValPredio(valPredio);

        listaAvaluoPredio.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_VALOR_PREDIO.getTitulo(), ComunUtil.generarFormatoMoneda(valPredio, formatoMonedaSistema), null, null));

        List<AvaluoDto> listaImpuestosPredio = generarImpuestoPredial(predio, valPredio, dominios, formatoMonedaSistema);
        listaAvaluoPredio.addAll(listaImpuestosPredio);

        return listaAvaluoPredio;
    }

    private List<AvaluoDto> obtenerValorEdificacion(Predios predio, List<Dominios> dominios, BigDecimal promedioFactores, String formatoMonedaSistema, SesionDto sesion) throws NewviExcepcion {
        BigDecimal areaConstruccion = BigDecimal.ZERO;
        BigDecimal valorEdificacion = BigDecimal.ZERO;
        List<AvaluoDto> listaAvaluoBloque;
        List<AvaluoDto> listaValorEdificacion = new ArrayList<>();
        List<AvaluoDto> listaEdificacion = new ArrayList<>();
        if (!ComunUtil.esNulo(predio.getBloquesActivos())) {
            for (Bloques bloque : predio.getBloquesActivos()) {
                if (bloque.getBloEstado().equals(EnumEstadoRegistro.A)) {
                    listaAvaluoBloque = obtenerAvaluoBloque(bloque, promedioFactores, dominios, formatoMonedaSistema, sesion);
                    listaEdificacion.add(generarElementoArbolAvaluo("Bloque: " + bloque.getNomBloque(), ComunUtil.generarFormatoMoneda(obtenerValorElementoAvaluoPorDescripcion(listaAvaluoBloque, EnumCaracteristicasAvaluo.BLOQUE_VALORACION.getTitulo(), formatoMonedaSistema), formatoMonedaSistema), null, listaAvaluoBloque));
                    //listaValorEdificacion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.BLOQUE_COSTO_TOTAL.getTitulo(), ComunUtil.generarFormatoMoneda(obtenerValorElementoAvaluoPorDescripcion(listaAvaluoBloque, EnumCaracteristicasAvaluo.BLOQUE_VALORACION.getTitulo(), formatoMonedaSistema), formatoMonedaSistema), null, null));
                    valorEdificacion = valorEdificacion.add(obtenerValorElementoAvaluoPorDescripcion(listaAvaluoBloque, EnumCaracteristicasAvaluo.BLOQUE_VALORACION.getTitulo(), formatoMonedaSistema));
                    areaConstruccion = areaConstruccion.add(obtenerValorElementoAvaluoPorDescripcion(listaAvaluoBloque, EnumCaracteristicasAvaluo.BLOQUE_AREA.getTitulo(), formatoMonedaSistema));
                }
            }
        }
        listaValorEdificacion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_TERRENO_AREA_CONSTRUCCION.getTitulo(), areaConstruccion.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
        listaValorEdificacion.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_VALOR_EDIFICACION.getTitulo(), ComunUtil.generarFormatoMoneda(valorEdificacion, formatoMonedaSistema), null, listaEdificacion));
        return listaValorEdificacion;
    }

    @Override
    public Predios actualizarValoresUbicacion(Predios predioActualizable, SesionDto sesion) throws NewviExcepcion {
        String codCatastral = predioActualizable.getNomCodigocatastralanterior();
        predioActualizable.setCodZona(codCatastral.substring(6, 8));
        predioActualizable.setCodSector(codCatastral.substring(8, 10));
        predioActualizable.setCodZona(codCatastral.substring(10, 12));
        predioActualizable.setCodManzana(codCatastral.substring(12, 14));
        predioActualizable.setCodPredio(codCatastral.substring(14, codCatastral.length()));
        actualizarPredio(predioActualizable, sesion);
        return predioActualizable;
    }

    private List<AvaluoDto> obtenerValorTerreno(Predios predio, List<Dominios> dominios, List<AvaluoDto> listaCoeficientes, BigDecimal promedioFactores, String formatoMonedaSistema) throws NewviExcepcion {
        String zona = predio.getCodZona();
        String sector = predio.getCodSector();
        String influencia = EnumZonaInfluencia.A.name();
        if (!ComunUtil.esNulo(predio.getCodZonaInfluencia())) {
            influencia = predio.getCodZonaInfluencia().toString();
        }

        BigDecimal valorMetro2, valorTerreno, valorInfluencia;
        BigDecimal area = !ComunUtil.esNulo(predio.getValAreaPredio()) ? predio.getValAreaPredio() : BigDecimal.ZERO;
        List<AvaluoDto> listaValorTerreno = new ArrayList<>();
        valorMetro2 = obtenerValorPorCodigoCalculo(dominios, "20" + zona + sector, "ZONAS VALORADAS M2");
        valorInfluencia = obtenerValorPorCodigoCalculo(dominios, "20" + zona + sector + influencia, "ZONAS VALORADAS M2");
        if (valorInfluencia.compareTo(BigDecimal.ZERO) == 0) {
            valorInfluencia = BigDecimal.ONE;
        }
        valorMetro2 = valorMetro2.multiply(valorInfluencia);
        valorTerreno = area.multiply(valorMetro2.multiply(promedioFactores));
        listaValorTerreno.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_PROMEDIO_FACTORES.getTitulo(), promedioFactores.setScale(2, BigDecimal.ROUND_UP).toString(), null, listaCoeficientes));
        listaValorTerreno.add(generarElementoArbolAvaluo("Precio base (m2): zona " + zona + " sector " + sector + " influencia " + influencia, valorMetro2.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
        listaValorTerreno.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_PRECIO_BASE.getTitulo(), valorMetro2.setScale(2, BigDecimal.ROUND_UP).toString(), null, null));
        listaValorTerreno.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_VALOR_TERRENO.getTitulo(), ComunUtil.generarFormatoMoneda(valorTerreno, formatoMonedaSistema), null, null));
        return listaValorTerreno;
    }

    private AvaluoDto generarCoeficienteFrenteFondo(Predios predio, List<Dominios> dominios) {

        BigDecimal frente = !ComunUtil.esNulo(predio.getValAreaFrente()) ? predio.getValAreaFrente() : BigDecimal.ZERO;
        BigDecimal area = !ComunUtil.esNulo(predio.getValAreaPredio()) ? predio.getValAreaPredio() : BigDecimal.ZERO;
        BigDecimal coeficienteFrenteFondo = BigDecimal.ZERO;
        if (!ComunUtil.esNulo(frente) && frente.compareTo(BigDecimal.ZERO) != 0) {
            coeficienteFrenteFondo = obtenerValoracionFondoRelativo(area, frente, dominios);
        }
        AvaluoDto elementoCoeficienteFrenteFondo = generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_TERRENO_FACTOR_FRENTE_FONDO.getTitulo(), null, quitarDecimales(coeficienteFrenteFondo), null);
        return elementoCoeficienteFrenteFondo;
    }

    private List<AvaluoDto> generarListadoInfoTerreno(Predios predio) {
        BigDecimal frente = !ComunUtil.esNulo(predio.getValAreaFrente()) ? predio.getValAreaFrente() : BigDecimal.ZERO;
        BigDecimal fondo = !ComunUtil.esNulo(predio.getValAreaFondo()) ? predio.getValAreaFondo() : BigDecimal.ZERO;
        BigDecimal area = !ComunUtil.esNulo(predio.getValAreaPredio()) ? predio.getValAreaPredio() : BigDecimal.ZERO;
        List<AvaluoDto> listadoInfoTerreno = new ArrayList<>();
        listadoInfoTerreno.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_TERRENO_AREA.getTitulo(), quitarDecimales(area), null, null));
        listadoInfoTerreno.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_TERRENO_FRENTE.getTitulo(), quitarDecimales(frente), null, null));
        listadoInfoTerreno.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_TERRENO_FONDO_RELATIVO.getTitulo(), quitarDecimales(fondo), null, null));
        return listadoInfoTerreno;
    }

    private List<AvaluoDto> generarCoeficientesTerreno(Predios predio, List<Dominios> dominios) {
        List<AvaluoDto> listaCoeficientesTerreno = new ArrayList<>();
        List<Dominios> listaDominios = obtenerDominiosPorGrupo(dominios, "DESCRIPCION DEL TERRENO");
        for (EnumCaracteristicasAvaluo enumCaracteristicasAvaluo : EnumCaracteristicasAvaluo.obtenerCaracteristicasAvaluoPorGrupo("DESCRIPCION DEL TERRENO")) {
            BigDecimal coeficiente = obtenerCoeficienteTerreno(predio, listaDominios, enumCaracteristicasAvaluo.getCalculo());
            listaCoeficientesTerreno.add(generarElementoArbolAvaluo(enumCaracteristicasAvaluo.getTitulo(), null, quitarDecimales(coeficiente), null));
        }
        listaCoeficientesTerreno.add(generarCoeficienteFrenteFondo(predio, dominios));
        return listaCoeficientesTerreno;
    }

    private List<AvaluoDto> generarCoeficientesServicios(Predios predio, List<Dominios> dominios) throws NewviExcepcion {
        List<AvaluoDto> listaCoeficientesServicios = new ArrayList<>();
        List<Dominios> listaDominios = obtenerDominiosPorGrupo(dominios, "INFRAESTRUCTURA DE SERVICIOS");
        for (EnumCaracteristicasAvaluo enumCaracteristicasAvaluo : EnumCaracteristicasAvaluo.obtenerCaracteristicasAvaluoPorGrupo("INFRAESTRUCTURA DE SERVICIOS")) {
            BigDecimal coeficiente = obtenerCoeficienteServicio(predio, listaDominios, enumCaracteristicasAvaluo.getCalculo());
            listaCoeficientesServicios.add(generarElementoArbolAvaluo(enumCaracteristicasAvaluo.getTitulo(), null, quitarDecimales(coeficiente), null));
        }
        return listaCoeficientesServicios;
    }

    private BigDecimal obtenerPromedioCoeficientes(List<AvaluoDto> listaCoeficientes, String formatoMonedaSistema) throws NewviExcepcion {
        BigDecimal promedioCoeficientes;
        BigDecimal sumaCoeficientes = BigDecimal.ZERO;
        for (AvaluoDto coeficiente : listaCoeficientes) {
            sumaCoeficientes = sumaCoeficientes.add(ComunUtil.obtenerNumeroDecimalDesdeTexto(coeficiente.getFactor(), formatoMonedaSistema));
        }
        promedioCoeficientes = sumaCoeficientes.divide(new BigDecimal(listaCoeficientes.size()), 4, RoundingMode.HALF_UP);
        return promedioCoeficientes;
    }

    private List<AvaluoDto> generarImpuestoPredial(Predios predio, BigDecimal avaluo, List<Dominios> dominios, String formatoMonedaSistema) throws NewviExcepcion {

        List<AvaluoDto> listaImpuestosPredio = new ArrayList<>();

        ConstantesImpuestos constantesImpuestos = parametrosServicio.obtenerConstantesImpuestosPorTipo("URBANO").get(0);
        BigDecimal valorImpuestoPredial = avaluo.multiply(constantesImpuestos.getValTasaaplicada());
        predio.setValImpuesto(valorImpuestoPredial);

        List<AvaluoDto> listaExoneraciones = determinarDescuentosYExoneraciones(predio, valorImpuestoPredial, dominios, formatoMonedaSistema);
        BigDecimal totalExoneraciones = obtenerValorElementoAvaluoPorDescripcion(listaExoneraciones, EnumCaracteristicasAvaluo.IMPUESTOS_EXONERACIONES_TOTAL.getTitulo(), formatoMonedaSistema);

        BigDecimal aPagar = valorImpuestoPredial.subtract(totalExoneraciones);

        List<AvaluoDto> listaOtrosRubros = determinarOtrosRubros(predio, avaluo, dominios, constantesImpuestos, formatoMonedaSistema);
        BigDecimal totalOtrosRubros = obtenerValorElementoAvaluoPorDescripcion(listaOtrosRubros, EnumCaracteristicasAvaluo.IMPUESTOS_OTROS_VALORES_TOTAL.getTitulo(), formatoMonedaSistema);

        aPagar = aPagar.add(totalOtrosRubros);

        predio.setValImppredial(aPagar);

        listaImpuestosPredio.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_IMPUESTO_PREDIAL.getTitulo(), ComunUtil.generarFormatoMoneda(valorImpuestoPredial, formatoMonedaSistema), null, null));
        listaImpuestosPredio.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_EXONERACIONES.getTitulo(), ComunUtil.generarFormatoMoneda(totalExoneraciones, formatoMonedaSistema), null, listaExoneraciones));
        listaImpuestosPredio.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_OTROS_VALORES.getTitulo(), ComunUtil.generarFormatoMoneda(totalOtrosRubros, formatoMonedaSistema), null, listaOtrosRubros));
        listaImpuestosPredio.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.PREDIO_A_PAGAR.getTitulo(), ComunUtil.generarFormatoMoneda(aPagar, formatoMonedaSistema), null, null));

        return listaImpuestosPredio;
    }

    private BigDecimal obtenerValorCEMActual(Predios codCatastral) {
        Calendar cal;
        int anioActual;
        BigDecimal valorTotalCEM;

        anioActual = ComunUtil.obtenerAnioDesdeFecha(null);
        valorTotalCEM = BigDecimal.ZERO;

        List<ContribucionMejoras> listaObrasCEM = cemFacade.buscarContribucionMejorasPorAnio(anioActual);
        if (!ComunUtil.esNulo(listaObrasCEM)) {
            for (ContribucionMejoras obraActual : listaObrasCEM) {
                valorTotalCEM = valorTotalCEM.add(obraActual.obtenerValorCEM(codCatastral));
            }
        }
        return valorTotalCEM;
    }

    private List<AvaluoDto> determinarOtrosRubros(Predios predio, BigDecimal avaluo, List<Dominios> dominios, ConstantesImpuestos constantesImpuestos, String formatoMonedaSistema) throws NewviExcepcion {

        List<AvaluoDto> listaOtrosRubros = new ArrayList<>();

        // Constantes catastro urbano
        BigDecimal tasaImpuestoBomberos = constantesImpuestos.getValBomberos();
        BigDecimal valorServiciosAdministrativos = constantesImpuestos.getValServiciosadministrativos();
        BigDecimal valorContribucionEspecialMejoras = obtenerValorCEMActual(predio);
        BigDecimal valorServiciosAmbientales = constantesImpuestos.getValAmbientales();

        // Solar no edificado
        BigDecimal coeficienteUbicacion = obtenerCoeficienteTerreno(predio, dominios, "OCUPACION");
        BigDecimal valorImpuestoNoEdificado = BigDecimal.ZERO;
        if (!ComunUtil.esNulo(coeficienteUbicacion)
                && coeficienteUbicacion.compareTo(BigDecimal.ZERO) != 0) {
            valorImpuestoNoEdificado = avaluo.multiply(coeficienteUbicacion);
            predio.setValNoEdificacion(valorImpuestoNoEdificado);
        }

        // Actualiza otros valores calculados
        predio.setValCem(valorContribucionEspecialMejoras);
        predio.setValBomberos(avaluo.multiply(tasaImpuestoBomberos));
        predio.setValEmision(valorServiciosAdministrativos);
        predio.setValAmbientales(valorServiciosAmbientales);

        listaOtrosRubros.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_BOMBEROS.getTitulo(), ComunUtil.generarFormatoMoneda(predio.getValBomberos(), formatoMonedaSistema), null, null));
        listaOtrosRubros.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_COSTO_EMISION.getTitulo(), ComunUtil.generarFormatoMoneda(predio.getValEmision(), formatoMonedaSistema), null, null));
        listaOtrosRubros.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_CEM.getTitulo(), ComunUtil.generarFormatoMoneda(predio.getValCem(), formatoMonedaSistema), null, null));
        listaOtrosRubros.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_SERVICIOS_AMBIENTALES.getTitulo(), ComunUtil.generarFormatoMoneda(predio.getValAmbientales(), formatoMonedaSistema), null, null));
        listaOtrosRubros.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_SOLAR_NO_EDIFICADO.getTitulo(), ComunUtil.generarFormatoMoneda(valorImpuestoNoEdificado, formatoMonedaSistema), null, null));

        BigDecimal totalOtrosRubros = valorServiciosAdministrativos.add(valorContribucionEspecialMejoras).add(valorServiciosAmbientales).add(avaluo.multiply(tasaImpuestoBomberos)).add(valorImpuestoNoEdificado);

        listaOtrosRubros.add(generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_OTROS_VALORES_TOTAL.getTitulo(), ComunUtil.generarFormatoMoneda(totalOtrosRubros, formatoMonedaSistema), null, null));

        return listaOtrosRubros;
    }

    private List<Dominios> unirListas(List<Dominios> a, List<Dominios> b) {
        a.addAll(b);
        return a;
    }

    private List<AvaluoDto> determinarDescuentosYExoneraciones(Predios predio, BigDecimal aPagar, List<Dominios> dominios, String formatoMonedaSistema) throws NewviExcepcion {
        BigDecimal totalImpuesto = aPagar;
        BigDecimal totalDescuento;
        BigDecimal factorDescuento = BigDecimal.ZERO;
        AvaluoDto nuevoExoneracion;
        List<AvaluoDto> listaDescuentos = new ArrayList<>();
        FichaCatastralDto fichaPredio = new FichaCatastralDto(predio);
        for (Dominios dominio : obtenerListaDominiosDeTenencia(fichaPredio.getPropiedad().getTenenciasActivas(), dominios)) {
            nuevoExoneracion = generarElementoArbolAvaluo(dominio.getDomiCalculo(), null, null, null);
            listaDescuentos.add(nuevoExoneracion);
            if ((new BigDecimal(dominio.getDomiCoefic())).compareTo(factorDescuento) >= 0) {
                factorDescuento = new BigDecimal(dominio.getDomiCoefic());
            }
        }
        totalDescuento = totalImpuesto.multiply(factorDescuento);
        nuevoExoneracion = generarElementoArbolAvaluo(EnumCaracteristicasAvaluo.IMPUESTOS_EXONERACIONES_TOTAL.getTitulo(), ComunUtil.generarFormatoMoneda(totalDescuento, formatoMonedaSistema), null, null);
        listaDescuentos.add(nuevoExoneracion);
        return listaDescuentos;
    }

    private List<Dominios> obtenerExoneracionPorCodigoYCalculo(List<Tenencia> tenencias, List<Dominios> dominios, String codigo, String calculo) {
        //List<Dominios> listaDominiosExoneracion = obtenerDominiosPorCodigoYCalculo(dominios, "1206", "DESCUENTO URBANO MARGINAL");
        List<Dominios> listaDominiosExoneracion = obtenerDominiosPorCodigoYCalculo(dominios, codigo, calculo);
        return obtenerDominioDeExoneracion(tenencias, listaDominiosExoneracion);
    }

    private List<Dominios> obtenerDominioDeExoneracion(List<Tenencia> tenencias, List<Dominios> listaDominios) {
        List<Dominios> listaFiltradaExoneraciones = new ArrayList<>();
        for (Tenencia tenencia : tenencias) {
            for (Dominios dominio : listaDominios) {
                if (dominio.getDomiCodigo().contains(tenencia.getStsCodigo().trim())) {
                    listaFiltradaExoneraciones.add(dominio);
                }
            }
        }
        return listaFiltradaExoneraciones;
    }

    private List<Dominios> obtenerListaDominiosDeTenencia(List<Tenencia> tenencias, List<Dominios> dominios) {
        List<Dominios> listaFiltradaExoneraciones = new ArrayList<>();
        if (!ComunUtil.esNulo(tenencias) && !tenencias.isEmpty()) {
            listaFiltradaExoneraciones = obtenerExoneracionPorCodigoYCalculo(tenencias, dominios, "1206", "DESCUENTO URBANO MARGINAL");
            listaFiltradaExoneraciones = !listaFiltradaExoneraciones.isEmpty() && listaFiltradaExoneraciones.get(0).getDomiDescripcion().equals("NO") ? obtenerExoneracionPorCodigoYCalculo(tenencias, dominios, "1205", "DESCUENTOS ESPECIALES CATASTRO") : listaFiltradaExoneraciones;
        }

        return listaFiltradaExoneraciones;
    }

    private BigDecimal obtenerCOFF(List<Dominios> dominios, BigDecimal valor, String calculo) {
        BigDecimal coff = new BigDecimal(BigInteger.ZERO);
        for (Dominios dominio : dominios) {
            if (dominio.getDomiMinimo() <= valor.doubleValue()
                    && dominio.getDomiMaximo() >= valor.doubleValue()
                    && dominio.getDomiCalculo().contains(calculo)) {
                coff = new BigDecimal(dominio.getDomiCoefic(), MathContext.DECIMAL32);
            }
        }
        return coff;
    }

    private BigDecimal obtenerValoracionFondoRelativo(BigDecimal area, BigDecimal frente, List<Dominios> dominios) {

        BigDecimal v1, v2;
        v1 = area.divide(frente, 4, RoundingMode.CEILING);
        v2 = frente.divide(v1, 4, RoundingMode.CEILING);

        //BigDecimal coff = parametrosServicio.obtenerCOFF(v2, "FACTOR FRENTE FONDO");
        BigDecimal coff = obtenerCOFF(dominios, v2, "FACTOR FRENTE FONDO");

        return coff;

    }

    private AvaluoDto generarElementoArbolAvaluo(String descripcion, String valor, String factor, List<AvaluoDto> hijos) {
        AvaluoDto nodoRaiz = new AvaluoDto(descripcion, valor, factor, hijos);
        return nodoRaiz;
    }

    private AvaluoDto generarNodoDetalle(PisoDetalle pisoDetalle, String elementoCalculo, List<Dominios> dominios) {
        //BigDecimal coeficiente = parametrosServicio.obtenerCoeficienteDetallePiso(pisoDetalle, elementoCalculo);
        BigDecimal coeficiente = obtenerCoeficienteDetallePiso(pisoDetalle, elementoCalculo, dominios);
        return generarElementoArbolAvaluo(pisoDetalle.getSubgrupo(), pisoDetalle.getDescripcion(), coeficiente.setScale(2, BigDecimal.ROUND_UP).toString(), null);
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
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoFechaAvaluo.setAudIngIp(sesion.getDireccionIP());
        nuevoFechaAvaluo.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());

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
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoAvaluo.setAudIngIp(sesion.getDireccionIP());
        nuevoAvaluo.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());

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
    public List<FechaAvaluo> consultarFechaAvaluos() {
        return fechaAvaluoFacade.buscarFechaAvaluos();
    }

    @Override
    public List<Avaluo> consultarListaAvaluosActuales() {
        Avaluo avaluoActual = avaluoFacade.consultarAvaluoActual();
        return avaluoFacade.consultarAvaluos(avaluoActual.getFecavId().getFechaDescripcion());
    }

    @Override
    public List<Avaluo> consultarListaAvaluosPorFecha(String fechaAvaluo) {
        return avaluoFacade.consultarAvaluos(fechaAvaluo);
    }

    @Override
    public Avaluo consultarAvaluoPorCodCatastralYFechaAvaluo(Predios codCatrastal, FechaAvaluo fecavId) {
        return avaluoFacade.consultarAvaluoPorCodCatastralYFechaAvaluo(codCatrastal, fecavId);
    }

    @Override
    public String actualizarAvaluo(Avaluo avaluo, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando avaluo...", sesion);
        if (!avaluo.esAvaluoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR362);
        }
        // Editando el avaluo
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Actualizando avaluo...", sesion);

        //Registramos la auditoria de ingreso
        Date fechaIngreso = Calendar.getInstance().getTime();
        avaluo.setAudIngIp(sesion.getDireccionIP());
        avaluo.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());

        avaluo.setAudIngFec(fechaIngreso);

        avaluoFacade.edit(avaluo);

        // Si todo marcha bien enviar id de avaluo
        return avaluo.getAvalId().toString();
    }

    @Override
    public List<DetallesAvaluo> consultarListaDetallesAvaluo(Integer codCatastral) {
        return detallesAvaluoFacade.buscarDetallesAvaluo(codCatastral);
    }

    @Override
    public Integer generarNuevoDetalleAvaluo(DetallesAvaluo nuevoDetalleAvaluo, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando detalle avaluo...", sesion);
        if (!nuevoDetalleAvaluo.esAvaluoValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR362);
        }
        // Crear el detalle de avaluo
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando fecha avaluo...", sesion);

        //Registramos la auditoria de ingreso
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoDetalleAvaluo.setAudIngIp(sesion.getDireccionIP());
        nuevoDetalleAvaluo.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());

        nuevoDetalleAvaluo.setAudIngFec(fechaIngreso);

        detallesAvaluoFacade.create(nuevoDetalleAvaluo);

        // Si todo marcha bien enviar id del detalle de avaluo
        return nuevoDetalleAvaluo.getDavalId();
    }

    @Override
    public List<DetallesAvaluo> consultarHijosDetallesAvaluo(DetallesAvaluo detallesAvaluo) {

        return detallesAvaluoFacade.buscarHijosDetallesAvaluo(detallesAvaluo);
    }

    private String obtenerRelacion(AvaluoDto hijo) {
        String relacion;
        if (hijo.getHijos() == null) {
            relacion = "Hijo";
        } else {
            relacion = "SubNodo";
        }
        return relacion;
    }

    public List<AvaluoDto> obtenerHijos(List<AvaluoDto> nodo) {
        List<AvaluoDto> hijos = new ArrayList<>();
        for (AvaluoDto avaluoDto : nodo) {
            hijos = avaluoDto.getHijos();
        }
        return hijos;
    }

    @Override
    public List<AvaluoDto> listarAvaluoDto(String relacion, Predios predio) {
        List<AvaluoDto> listaAvaluoDto = new ArrayList<>();

        for (DetallesAvaluo nuevoDetalle : detallesAvaluoFacade.buscarDetallesAvaluoNodo(relacion, predio)) {
            listaAvaluoDto.add(new AvaluoDto(nuevoDetalle, this));
        }
        return obtenerHijos(listaAvaluoDto);
    }

    @Override
    public DetallesAvaluo consultarPadre(Predios predio, String relacion) {
        return detallesAvaluoFacade.buscarPadre(predio, relacion);
    }

    @Override
    public void eliminarDetallesPorPredio(Predios predio) {
        detallesAvaluoFacade.eliminarDetallesPorPredio(predio);
    }

    private String quitarEspacios(String valor) {
        if (valor != null) {
            valor = valor.trim();
        }
        return valor;
    }

    private Boolean esNodo(AvaluoDto nodo) {
        Boolean retorno = false;
        if (nodo.getHijos() != null) {
            retorno = true;
        }
        return retorno;
    }

    private void registrarNodos(AvaluoDto nodo, Integer aux, Integer codigoPadre, SesionDto sesion, Predios predio) throws NewviExcepcion {
        if (esNodo(nodo)) {
            codigoPadre = aux;
            for (AvaluoDto hijo : nodo.getHijos()) {
                aux = registrarDetalleAvaluo(hijo, codigoPadre, obtenerRelacion(hijo), sesion, predio);
                registrarNodos(hijo, aux, codigoPadre, sesion, predio);
            }
        }
    }

    private Integer registrarDetalleAvaluo(AvaluoDto nodo, Integer codigoPadre, String relacion, SesionDto sesion, Predios predio) throws NewviExcepcion {
        DetallesAvaluo detallesAvaluo = new DetallesAvaluo();
        detallesAvaluo.setCodCatastral(predio);
        detallesAvaluo.setDavalDescripcion(nodo.getDescripcion());
        detallesAvaluo.setDavalValor(quitarEspacios(nodo.getValor()));
        detallesAvaluo.setDavalRelacion(quitarEspacios(relacion));
        detallesAvaluo.setDavalPadre(codigoPadre.toString());
        detallesAvaluo.setDavalEstado(EnumEstadoRegistro.A);
        detallesAvaluo.setDavalFactor(nodo.getFactor());
        return generarNuevoDetalleAvaluo(detallesAvaluo, sesion);
    }

    private void generarNodos(AvaluoDto nodo, Integer codigoPadre, String relacion, SesionDto sesion, Predios predio) throws NewviExcepcion {
        int aux;
        aux = registrarDetalleAvaluo(nodo, codigoPadre, relacion, sesion, predio);
        registrarNodos(nodo, aux, codigoPadre, sesion, predio);
    }

    public Integer obtenerCodigoPadre(Predios predio, SesionDto sesion) throws NewviExcepcion {
        eliminarDetallesPorPredio(predio);
        generarNodos(generarElementoArbolAvaluo("Arbol Avaluo", null, null, null), 0, "Nodo", sesion, predio);
        return (detallesAvaluoFacade.buscarPadre(predio, "Nodo")).getDavalId();
    }

    @Override
    public void registrarArbol(List<AvaluoDto> nodo, Predios predio, SesionDto sesion) throws NewviExcepcion {
        Integer padre = obtenerCodigoPadre(predio, sesion);
        for (AvaluoDto avaluoDto : nodo) {
            generarNodos(avaluoDto, padre, "SubNodo", sesion, predio);
        }
    }

    @Override
    public String generarNuevoLogPredio(LogPredio nuevoLogPredio, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando logPredio...", sesion);
        if (!nuevoLogPredio.esLogPredioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR338);
        }
        // Crear el logPredio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando logPredio...", sesion);

        /*Registramos la auditoria de ingreso
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoLogPredio.setAudIngIp(sesion.getDireccionIP());
        nuevoLogPredio.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        nuevoLogPredio.setAudIngFec(fechaIngreso);*/
        logPredioFacade.create(nuevoLogPredio);
        // Si todo marcha bien enviar nombre del logPredio
        return nuevoLogPredio.getCodUsu();
    }

    @Override
    public String actualizarLogPredio(LogPredio logPredio, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando logPredio...", sesion);
        if (!logPredio.esLogPredioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR338);
        }
        // Editar la logPredio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando logPredio...", sesion);

        /*Registramos la auditoria de modificacion
        Date fechaModificacion = Calendar.getInstance().getTime();
        predio.setAudModIp(sesion.getDireccionIP());
        predio.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        predio.setAudModFec(fechaModificacion);*/
        logPredioFacade.edit(logPredio);

        // Si todo marcha bien enviar nombre de la logPredio
        return logPredio.getCodUsu();
    }

    @Override
    public LogPredio seleccionarLogPredio(Integer codLogPredio) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(codLogPredio)) {
            return logPredioFacade.find(codLogPredio);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public List<LogPredio> consultarLogPredio() {
        return logPredioFacade.buscarLogPredio();
    }

    @Override
    public String eliminarLogPredio(LogPredio logPredio, SesionDto sesion) throws NewviExcepcion {
        logPredio.setLogEstado(EnumEstadoRegistro.A);
        return actualizarLogPredio(logPredio, sesion);
    }

    @Override
    public String generarLogPredio(Predios predio) throws NewviExcepcion {
        String log = predio.esPredioIgual(predio, seleccionarPredio(predio.getCodCatastral()));
        if (!ComunUtil.esCadenaVacia(log)) {
            return log.replaceAll("^\\s*", "");
        } else {
            return "No existen cambios en el predio";
        }
    }

    @Override
    public String generarLogServicios(Servicios servicio) throws NewviExcepcion {
        Servicios servicioBase = seleccionarServicio(servicio.getCodServicios());
        String log = servicio.esObjetoIgual(servicio, servicioBase);
        if (!ComunUtil.esCadenaVacia(log)) {
            return log.replaceAll("^\\s*", "");
        } else {
            return "";
        }
    }

    @Override
    public Servicios seleccionarServicio(Integer codServicio) throws NewviExcepcion {
        if (ComunUtil.esNumeroPositivo(codServicio)) {
            return servicioFacade.find(codServicio);
        } else {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR011);
        }
    }

    @Override
    public String actualizarServicio(Servicios servicio, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando servicio...", sesion);
        if (!servicio.esServicioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR365);
        }
        // Editar la servicio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Editando servicio...", sesion);

        //Registramos la auditoria de modificacion
        Date fechaModificacion = Calendar.getInstance().getTime();
        servicio.setAudModIp(sesion.getDireccionIP());
        servicio.setAudModUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        servicio.setAudModFec(fechaModificacion);

        servicioFacade.edit(servicio);

        // Si todo marcha bien enviar nombre del servicio
        return servicio.getCodServicios().toString();
    }

    @Override
    public String generarNuevoServicio(Servicios nuevoServicio, SesionDto sesion) throws NewviExcepcion {
        // Validar que los datos no sean incorrectos
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Validando predio...", sesion);
        if (!nuevoServicio.esServicioValido()) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR365);
        }
        // Crear el servicio
        LoggerNewvi.getLogNewvi(this.getClass()).debug("Creando servicio...", sesion);

        //Registramos la auditoria de ingreso
        Date fechaIngreso = Calendar.getInstance().getTime();
        nuevoServicio.setAudIngIp(sesion.getDireccionIP());
        nuevoServicio.setAudIngUsu(sesion.getUsuarioRegistrado().getUsuPalabraclave().trim());
        nuevoServicio.setAudIngFec(fechaIngreso);

        servicioFacade.create(nuevoServicio);
        // Si todo marcha bien enviar nombre del servicio
        return nuevoServicio.getCodServicios().toString();
    }

}
