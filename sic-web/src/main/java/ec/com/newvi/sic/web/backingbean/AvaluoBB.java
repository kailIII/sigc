/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.newvi.sic.web.backingbean;

import ec.com.newvi.sic.dto.AvaluoDto;
import ec.com.newvi.sic.dto.FichaCatastralDto;
import ec.com.newvi.sic.dto.TablaCatastralDto;
import ec.com.newvi.sic.enums.EnumCaracteristicasAvaluo;
import ec.com.newvi.sic.enums.EnumEstadoRegistro;
import ec.com.newvi.sic.enums.EnumNewviExcepciones;
import ec.com.newvi.sic.enums.EnumParametroSistema;
import ec.com.newvi.sic.enums.EnumReporte;
import ec.com.newvi.sic.modelo.Avaluo;
import ec.com.newvi.sic.modelo.Contribuyentes;
import ec.com.newvi.sic.modelo.Dominios;
import ec.com.newvi.sic.modelo.FechaAvaluo;
import ec.com.newvi.sic.modelo.Predios;
import ec.com.newvi.sic.util.ComunUtil;
import ec.com.newvi.sic.util.excepciones.NewviExcepcion;
import ec.com.newvi.sic.util.logs.LoggerNewvi;
import ec.com.newvi.sic.web.MensajesFaces;
import ec.com.newvi.sic.web.enums.EnumEtiquetas;
import ec.com.newvi.sic.web.enums.EnumPantallaMantenimiento;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author Andrés
 */
@ManagedBean
@ViewScoped
public class AvaluoBB extends AdminFichaCatastralBB {

    private EnumPantallaMantenimiento pantallaActual;
    private Boolean skip;
    private List<Avaluo> listaAvaluos;
    private List<Avaluo> listaAvaluosFiltrados;
    private List<FechaAvaluo> listaFechaAvaluos;
    private Integer progreso;
    private FechaAvaluo fechaAvaluoActual;
    private String fechaActualPrueba;
    private Boolean esActivo;
    private Boolean esProcesoIniciado;
    private Boolean esProcesoCancelado;

    public Boolean getEsProcesoCancelado() {
        return esProcesoCancelado;
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    public Boolean getEsProcesoIniciado() {
        return esProcesoIniciado;
    }

    public void setEsProcesoIniciado(Boolean esProcesoIniciado) {
        this.esProcesoIniciado = esProcesoIniciado;
    }

    public String getFechaActualPrueba() {
        return fechaActualPrueba;
    }

    public void setFechaActualPrueba(String fechaActualPrueba) {
        this.fechaActualPrueba = fechaActualPrueba;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public List<FechaAvaluo> getListaFechaAvaluos() {
        return listaFechaAvaluos;
    }

    public void setListaFechaAvaluos(List<FechaAvaluo> listaFechaAvaluos) {
        this.listaFechaAvaluos = listaFechaAvaluos;
    }

    public List<Avaluo> getListaAvaluos() {
        return listaAvaluos;
    }

    public void setListaAvaluos(List<Avaluo> listaAvaluos) {
        this.listaAvaluos = listaAvaluos;
    }

    public FechaAvaluo getFechaAvaluoActual() {
        return fechaAvaluoActual;
    }

    public void setFechaAvaluoActual(FechaAvaluo fechaAvaluoActual) {
        this.fechaAvaluoActual = fechaAvaluoActual;
    }

    public List<Avaluo> getListaAvaluosFiltrados() {
        return listaAvaluosFiltrados;
    }

    public void setListaAvaluosFiltrados(List<Avaluo> listaAvaluosFiltrados) {
        this.listaAvaluosFiltrados = listaAvaluosFiltrados;
    }

    public Boolean getSkip() {
        return skip;
    }

    public void setSkip(Boolean skip) {
        this.skip = skip;
    }

    @PostConstruct
    public void init() {
        this.esActivo = false;
        this.esProcesoIniciado = false;
        this.esProcesoCancelado = false;
        this.progreso = 0;
        fechaAvaluoActual = new FechaAvaluo();
        listaAvaluos = new ArrayList<>();
        listaFechaAvaluos = new ArrayList<>();
        conmutarPantalla(EnumPantallaMantenimiento.PANTALLA_LISTADO);
        establecerTitulo(EnumEtiquetas.SIMULACION_LISTA_TITULO,
                EnumEtiquetas.SIMULACION_LISTA_ICONO,
                EnumEtiquetas.SIMULACION_LISTA_DESCRIPCION);
        this.skip = false;
        actualizarListadoFechaAvaluos();
        //actualizarListadoAvaluos();

    }

    public FechaAvaluo generarFechaAvaluo() throws NewviExcepcion {
        FechaAvaluo fechaAvaluo = new FechaAvaluo();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = Calendar.getInstance().getTime();

        fechaAvaluo.setFecavFechaavaluo(fecha);
        fechaAvaluo.setFecavEstado(EnumEstadoRegistro.A);
        fechaAvaluo.setFechaDescripcion(formato.format(fecha));

        return catastroServicio.generarNuevaFechaAvaluo(fechaAvaluo, sesionBean.getSesion());
    }

    public List<FichaCatastralDto> generarListaFichaCatastral() {
        List<Predios> listaPredios;
        List<FichaCatastralDto> listaFichas = new ArrayList<>();

        listaPredios = catastroServicio.consultarPredios();

        for (Predios elementoPredio : listaPredios) {
            listaFichas.add(new FichaCatastralDto(elementoPredio));
        }

        return listaFichas;
    }

    public void abrirModalEspera() throws NewviExcepcion {
        //WebUtils.obtenerContextoPeticion().execute("PF('calcularSimulacion').disable()");
        //generarSimulacion();
        generarSimulacionFinal();
    }

    public void cancelarAvaluo() {
        this.esProcesoCancelado = true;
    }

    public void generarSimulacionFinal() throws NewviExcepcion {
        this.esProcesoIniciado = true;

        String formatoMonedaSistema = parametrosServicio.obtenerParametroPorNombre(EnumParametroSistema.FORMATO_MONEDAS, sesionBean.getSesion()).getValor();

        List<Predios> listaPredios = catastroServicio.consultarPredios();
        List<Dominios> dominios = parametrosServicio.consultarDominios();
        int cont = 0;

        for (Predios predioACalcular : listaPredios) {
            List<AvaluoDto> calculoAvaluo = catastroServicio.obtenerAvaluoPredio(predioACalcular, dominios, formatoMonedaSistema, sesionBean.getSesion());
            catastroServicio.registrarArbol(calculoAvaluo, predioACalcular, sesionBean.getSesion());
            //generarElementoAvaluo(calculoAvaluo, formatoMonedaSistema);
            LoggerNewvi.getLogNewvi(this.getClass()).info(predioACalcular.getCodCatastral(), sesionBean.getSesion());
            if (this.progreso <= 100) {
                if ((cont++ % (listaPredios.size() / 100)) == 0) {
                    progreso++;
                }
            } else {
                this.progreso = 100;
            }
            if (esProcesoCancelado) {
                break;
            }
        }
    }

    public void generarSimulacion() throws NewviExcepcion {

        this.esActivo = true;

        Avaluo avaluo;
        int cont = 0;
        String formatoMonedaSistema = parametrosServicio.obtenerParametroPorNombre(EnumParametroSistema.FORMATO_MONEDAS, sesionBean.getSesion()).getValor();

        FechaAvaluo fecavId = generarFechaAvaluo();
        List<FichaCatastralDto> listaFichas = generarListaFichaCatastral();
        List<Dominios> dominios = parametrosServicio.consultarDominios();

        for (FichaCatastralDto fichaDto : listaFichas) {
            Predios predioACalcular = fichaDto.getPredio();
            Contribuyentes contribuyente = fichaDto.getContribuyentePropiedad();
            //List<AvaluoDto> calculoAvaluo = catastroServicio.obtenerAvaluoPredio(predio, sesionBean.obtenerSesionDto());
            catastroServicio.obtenerAvaluoPredio(predioACalcular, dominios, formatoMonedaSistema, sesionBean.getSesion());
            avaluo = new Avaluo();
            //if (!(calculoAvaluo == null)) {
            if (Boolean.TRUE) {
                avaluo.setValTerreno(predioACalcular.getValTerreno());
                avaluo.setValPredio(predioACalcular.getValPredio());
                avaluo.setValImpuesto(predioACalcular.getValImppredial());
                avaluo.setValAreapredio(predioACalcular.getValImppredial());
                avaluo.setValEdifica(predioACalcular.getValEdifica());
                avaluo.setValAreaconstruccion(predioACalcular.getValAreaConstruccion());
                avaluo.setValBomberos(predioACalcular.getValBomberos());
                avaluo.setValEmision(predioACalcular.getValEmision());
                avaluo.setValCem(predioACalcular.getValCem());
                avaluo.setValAmbientales(predioACalcular.getValAmbientales());
                avaluo.setValNoEdificacion(predioACalcular.getValNoEdificacion());

                avaluo.setValImppredial(predioACalcular.getValImpuesto());
            }
            avaluo.setCodCatastral(predioACalcular);
            avaluo.setNomCodigocatastral(predioACalcular.getNomCodigocatastral());
            avaluo.setTxtDireccion(predioACalcular.getTxtDireccion());
            avaluo.setStsBarrio(predioACalcular.getStsBarrio());
            avaluo.setCodCedularuc(contribuyente.getCodCedularuc());
            avaluo.setNomnomape(contribuyente.getNomNombres().trim() + " " + contribuyente.getNomApellidos().trim());
            avaluo.setFecavId(fecavId);
            avaluo.setAvalEstado(EnumEstadoRegistro.A);
            catastroServicio.generarNuevoAvaluo(avaluo, sesionBean.getSesion());

            //LoggerNewvi.getLogNewvi(this.getClass()).debug(cont++, sesionBean.getSesion());
            LoggerNewvi.getLogNewvi(this.getClass()).info(predioACalcular.getCodCatastral(), sesionBean.getSesion());
            if (this.progreso <= 100) {
                if ((cont++ % (listaFichas.size() / 100)) == 0) {
                    progreso++;
                    //cont=0;
                }
            } else {
                this.progreso = 100;
            }

        }

        actualizarListadoAvaluos();

    }

    private void conmutarPantalla(EnumPantallaMantenimiento nuevaPantalla) {
        this.pantallaActual = nuevaPantalla;
    }

    public Boolean esPantallaActual(String pantallaEsperada) {
        try {
            return this.pantallaActual.equals(EnumPantallaMantenimiento.obtenerPantallaPorNombre(pantallaEsperada));
        } catch (NewviExcepcion e) {
            LoggerNewvi.getLogNewvi(this.getClass()).error(e, sesionBean.getSesion());
            MensajesFaces.mensajeError(e.getMessage());
            return false;
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void actualizarListadoFechaAvaluos() {
        listaFechaAvaluos = catastroServicio.consultarFechaAvaluos();
    }

    public void actualizarListadoAvaluos() {
        listaAvaluos = catastroServicio.consultarListaAvaluosActuales();
    }

    public void actualizarListaAvaluosPorFecha(String fechaDescripcion) {
        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //listaAvaluos = catastroServicio.consultarListaAvaluosPorFecha(formato.parse(fechaDescripcion));
            listaAvaluos = catastroServicio.consultarListaAvaluosPorFecha(fechaDescripcion);
        } catch (Exception e) {
            LoggerNewvi.getLogNewvi(this.getClass()).error(EnumNewviExcepciones.ERR001.presentarMensajeCodigo(), e, sesionBean.getSesion());
            MensajesFaces.mensajeError(e.getMessage());
        }

    }

    public void onComplete() {
        this.esProcesoIniciado = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Progress Completed"));
    }

    public DefaultStreamedContent imprimir(EnumReporte tipoReporte) {
        return generarReporteCatastro(tipoReporte);
    }

    public List<Field> filtrarFieldDecimales(Field[] metodosClase) {
        List<Field> metodosBuscado = new ArrayList<>();
        for (Field metodo : metodosClase) {
            if (metodo.getType().getName().contains("BigDecimal")) {
                metodosBuscado.add(metodo);
            }
        }
        return metodosBuscado;
    }

    public List<Method> obtenerSetters(Method[] listaMetodos) {
        List<Method> listaMetodosFiltrada = new ArrayList<>();
        for (Method metodo : listaMetodos) {
            if (metodo.getName().startsWith("set")) {
                listaMetodosFiltrada.add(metodo);
            }
        }
        return listaMetodosFiltrada;
    }

    public Method filtrarMetodos(Method[] listaMetodos, String nombreMetodo) {
        for (Method metodo : listaMetodos) {
            if (metodo.getName().contains(nombreMetodo)) {
                return metodo;
            }
        }
        return null;
    }

    public String renombrarMetodo(String metodo) {
        return "set".concat(metodo.substring(0, 1).toUpperCase().concat(metodo.substring(1, metodo.length())));
    }

    public Avaluo setearDatosAvaluo(Avaluo objetoAvaluo, BigDecimal valor, String metodo, Class claseAvaluo) throws NewviExcepcion {

        try {
            Method metodoEjecutable = filtrarMetodos(objetoAvaluo.getClass().getMethods(), renombrarMetodo(metodo));
            //try {
            //String metodo2 = renombrarMetodo(metodo);
            
            PropertyDescriptor objPropertyDescriptor = new PropertyDescriptor(renombrarMetodo(metodo), claseAvaluo);
       
            /*metodoEjecutable.invoke(claseAvaluo.getMethod(renombrarMetodo(metodo), claseAvaluo), valor);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR020, ex);
            } catch (NoSuchMethodException | SecurityException ex) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR020, ex);
            }*/
            return objetoAvaluo;
        } catch (IntrospectionException ex) {
            throw new NewviExcepcion(EnumNewviExcepciones.ERR020, ex);
        }
        //return null;
    }

    public void generarElementoAvaluo(List<AvaluoDto> AvaluoCalculado, String formatoMonedaSistema) throws NewviExcepcion {
        Avaluo avaluo = new Avaluo();
        Class claseAvaluo = avaluo.getClass();
        String nombreMetodoBuscado = "";
        EnumCaracteristicasAvaluo[] caracteristicasAvaluo = EnumCaracteristicasAvaluo.values();
        //Field[] metodosClase = claseAvaluo.getDeclaredFields();
        List<Field> metodosClase = filtrarFieldDecimales(claseAvaluo.getDeclaredFields());
        for (Field metodo : metodosClase) {
            nombreMetodoBuscado = metodo.getName();
            for (EnumCaracteristicasAvaluo caracteristica : caracteristicasAvaluo) {
                if (!ComunUtil.esNulo(caracteristica.getCampo()) && caracteristica.getCampo().equals(nombreMetodoBuscado)) {
                    BigDecimal valorAtributo = catastroServicio.obtenerElementoAvaluoPorDescripcion(AvaluoCalculado, caracteristica.getTitulo(), formatoMonedaSistema);
                    avaluo = setearDatosAvaluo(avaluo, valorAtributo, caracteristica.getCampo(), claseAvaluo);

                } else if (caracteristica.getCampo().equals("Tiene hijos")) {
                }
            }
        }
    }

    public List<TablaCatastralDto> obtenerListadoAvaluos(List<Avaluo> listaAvaluos) {
        List<TablaCatastralDto> datosImpresion = new ArrayList<>();
        TablaCatastralDto datosAvaluo;
        for (Avaluo avaluo : listaAvaluos) {
            datosAvaluo = new TablaCatastralDto();
            datosAvaluo.setCodigoCatastral(avaluo.getCodCatastral().getCodCatastral().toString());
            datosAvaluo.setNombreCodigoCatastral(avaluo.getNomCodigocatastral());
            datosAvaluo.setPropietario(avaluo.getNomnomape());
            datosAvaluo.setCiRuc(avaluo.getCodCedularuc());
            datosAvaluo.setBarrio(avaluo.getStsBarrio());
            datosAvaluo.setDireccion(avaluo.getTxtDireccion());
            datosAvaluo.setAvaluoTerreno(avaluo.getValTerreno());
            datosAvaluo.setAreaTerreno(avaluo.getValTerreno());
            datosAvaluo.setAreaEdificacion(avaluo.getValAreaconstruccion());
            datosAvaluo.setAvaluoEdificacion(avaluo.getValEdifica());
            datosAvaluo.setAvaluoPredio(avaluo.getValPredio());
            datosAvaluo.setAreaPredio(avaluo.getValAreapredio());
            datosAvaluo.setImpuestoPredial(avaluo.getValImpuesto());
            datosAvaluo.setContribucionEspecialMejoras(avaluo.getValCem());
            datosAvaluo.setTasaNoEdificacion(avaluo.getValNoEdificacion());
            datosAvaluo.setCostoEmision(avaluo.getValEmision());
            datosAvaluo.setTasaBomberos(avaluo.getValBomberos());
            datosAvaluo.setServiciosAmbientales(avaluo.getValAmbientales());
            datosAvaluo.setTotalAPagar(avaluo.getValImppredial());
            datosAvaluo.setObservaciones(avaluo.getCatCasosespeciales());
            datosImpresion.add(datosAvaluo);
        }
        return datosImpresion;
    }

    public List<Avaluo> generarListaAvaluo() {
        return catastroServicio.consultarListaAvaluosActuales();
    }

}
