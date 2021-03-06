/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.newvi.sic.dto;

import ec.com.newvi.sic.enums.EnumEstadoTitulo;
import ec.com.newvi.sic.modelo.Bloques;
import ec.com.newvi.sic.modelo.Contribuyentes;
import ec.com.newvi.sic.modelo.LogPredio;
import ec.com.newvi.sic.modelo.Predios;
import ec.com.newvi.sic.modelo.Propiedad;
import ec.com.newvi.sic.modelo.Servicios;
import ec.com.newvi.sic.modelo.Terreno;
import ec.com.newvi.sic.modelo.TituloMovimientos;
import ec.com.newvi.sic.modelo.Titulos;
import ec.com.newvi.sic.util.ComunUtil;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andrés
 */
public class PresentacionFichaCatastralDto {

    private String stsTipo;
    private String nomCodigocatastral;
    private String nomCodigocatastralanterior;
    private String codDpa;
    private String codZona;
    private String codCatastral;
    private String codCampo;
    private String codSector;
    private String codManzana;
    private String codPredio;
    private String codRegimentenencia;
    private String codHorizontal;
    private String stsBarrio;
    private String txtDireccion;
    private String txtCallePrincipal;
    private String txtObservacion;
    private String nomNumero;
    private String nomCartografia;
    private String nomFotoAerea;
    private String nomcCartografiaOtros;
    private String codigoTitulo;
    private BigDecimal valAreaPredio;
    private BigDecimal valAreaFrente;
    private BigDecimal valAreaFondo;
    private BigDecimal valAreaConstruccion;
    private BigDecimal valCoordenadaEste;
    private BigDecimal valCoordenadaNorte;
    private BigDecimal valTerreno;
    private BigDecimal valEdifica;
    private BigDecimal valPredio;
    private BigDecimal valCem;
    private BigDecimal valBomberos;
    private BigDecimal valEmision;
    private BigDecimal valNoEdificacion;
    private BigDecimal valAmbientales;
    private BigDecimal valImpuesto;
    private BigDecimal valServiciosadministrativos;
    private BigDecimal valImppredial;
    private BigDecimal valContruccionObsoleta;
    private BigDecimal valDescuentoExoneracion;
    private BigDecimal valInteresaplicado;
    private BigDecimal valDescuentoaplicado;
    private BigDecimal valPagado;

    private BigDecimal valDescuentoPago;
    private BigDecimal valRecargoPago;

    private String txtNorte;
    private String txtSur;
    private String txtEste;
    private String txtOeste;
    private String contribuyente;

    private String razonMovimiento;

    private String stsTenencia;
    private String stsTenenciaotro;
    private String stsTransferenciadominio;
    private BigDecimal valPredioAreaEscritura;
    private String txtNotaria;
    private String txtCiudad;
    private String txtRegistroNumero;
    private String txtDetalleRegistro;
    private String txtInformante;
    private String txtInformanteRelacion;
    private String stsSituacion;
    private String stsEscritura;
    private String fecInscripcion;
    private String fecEscritura;
    private String fecRegistro;
    private String stsEstado;

    public String getCodCatastral() {
        return codCatastral;
    }

    public void setCodCatastral(String codCatastral) {
        this.codCatastral = codCatastral;
    }

    public String getCodCampo() {
        return codCampo;
    }

    public void setCodCampo(String codCampo) {
        this.codCampo = codCampo;
    }

    private String codCedularuc;
    private String nomApellidos;
    private String nomNombres;
    private String txtTelefono;
    private String txtEmail;
    private String nomCiudadDomicilio;
    private String nombreRepresentante;
    private String cedulaRepresentante;
    private String direccionRepresentante;
    private String fecEmision;
    private String fecPago;
    private String anioEmision;

    /*LogPredio*/
    private String codUsu;
    private String fecLog;
    private String nomIp;
    private String txtLog;
    private String logAccion;

    private List<Terreno> listaDescripcionTerreno;

    private List<Bloques> listaBloques;

    private List<Servicios> listaServicios;

    public BigDecimal getValDescuentoPago() {
        return valDescuentoPago;
    }

    public void setValDescuentoPago(BigDecimal valDescuentoPago) {
        this.valDescuentoPago = valDescuentoPago;
    }

    public BigDecimal getValRecargoPago() {
        return valRecargoPago;
    }

    public void setValRecargoPago(BigDecimal valRecargoPago) {
        this.valRecargoPago = valRecargoPago;
    }

    public String getRazonMovimiento() {
        return razonMovimiento;
    }

    public void setRazonMovimiento(String razonMovimiento) {
        this.razonMovimiento = razonMovimiento;
    }

    public String getCodigoTitulo() {
        return codigoTitulo;
    }

    public void setCodigoTitulo(String codigoTitulo) {
        this.codigoTitulo = codigoTitulo;
    }

    public String getCodUsu() {
        return codUsu;
    }

    public void setCodUsu(String codUsu) {
        this.codUsu = codUsu;
    }

    public String getFecLog() {
        return fecLog;
    }

    public void setFecLog(String fecLog) {
        this.fecLog = fecLog;
    }

    public String getNomIp() {
        return nomIp;
    }

    public void setNomIp(String nomIp) {
        this.nomIp = nomIp;
    }

    public String getTxtLog() {
        return txtLog;
    }

    public void setTxtLog(String txtLog) {
        this.txtLog = txtLog;
    }

    public String getLogAccion() {
        return logAccion;
    }

    public void setLogAccion(String logAccion) {
        this.logAccion = logAccion;
    }

    public List<Bloques> getListaBloques() {
        return listaBloques;
    }

    public void setListaBloques(List<Bloques> listaBloques) {
        this.listaBloques = listaBloques;
    }

    public List<Servicios> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<Servicios> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public BigDecimal getValCoordenadaEste() {
        return valCoordenadaEste;
    }

    public void setValCoordenadaEste(BigDecimal valCoordenadaEste) {
        this.valCoordenadaEste = valCoordenadaEste;
    }

    public String getNomNombres() {
        return nomNombres;
    }

    public void setNomNombres(String nomNombres) {
        this.nomNombres = nomNombres;
    }

    public String getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(String txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getNomCiudadDomicilio() {
        return nomCiudadDomicilio;
    }

    public void setNomCiudadDomicilio(String nomCiudadDomicilio) {
        this.nomCiudadDomicilio = nomCiudadDomicilio;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getCedulaRepresentante() {
        return cedulaRepresentante;
    }

    public void setCedulaRepresentante(String cedulaRepresentante) {
        this.cedulaRepresentante = cedulaRepresentante;
    }

    public String getDireccionRepresentante() {
        return direccionRepresentante;
    }

    public void setDireccionRepresentante(String direccionRepresentante) {
        this.direccionRepresentante = direccionRepresentante;
    }

    public String getCodCedularuc() {
        return codCedularuc;
    }

    public void setCodCedularuc(String codCedularuc) {
        this.codCedularuc = codCedularuc;
    }

    public String getNomApellidos() {
        return nomApellidos;
    }

    public void setNomApellidos(String nomApellidos) {
        this.nomApellidos = nomApellidos;
    }

    public String getStsTipo() {
        return stsTipo;
    }

    public void setStsTipo(String stsTipo) {
        this.stsTipo = stsTipo;
    }

    public BigDecimal getValCoordenadaNorte() {
        return valCoordenadaNorte;
    }

    public void setValCoordenadaNorte(BigDecimal valCoordenadaNorte) {
        this.valCoordenadaNorte = valCoordenadaNorte;
    }

    public BigDecimal getValTerreno() {
        return valTerreno;
    }

    public void setValTerreno(BigDecimal valTerreno) {
        this.valTerreno = valTerreno;
    }

    public BigDecimal getValEdifica() {
        return valEdifica;
    }

    public void setValEdifica(BigDecimal valEdifica) {
        this.valEdifica = valEdifica;
    }

    public BigDecimal getValPredio() {
        return valPredio;
    }

    public void setValPredio(BigDecimal valPredio) {
        this.valPredio = valPredio;
    }

    public BigDecimal getValCem() {
        return valCem;
    }

    public void setValCem(BigDecimal valCem) {
        this.valCem = valCem;
    }

    public BigDecimal getValBomberos() {
        return valBomberos;
    }

    public void setValBomberos(BigDecimal valBomberos) {
        this.valBomberos = valBomberos;
    }

    public BigDecimal getValEmision() {
        return valEmision;
    }

    public void setValEmision(BigDecimal valEmision) {
        this.valEmision = valEmision;
    }

    public BigDecimal getValNoEdificacion() {
        return valNoEdificacion;
    }

    public void setValNoEdificacion(BigDecimal valNoEdificacion) {
        this.valNoEdificacion = valNoEdificacion;
    }

    public BigDecimal getValAmbientales() {
        return valAmbientales;
    }

    public void setValAmbientales(BigDecimal valAmbientales) {
        this.valAmbientales = valAmbientales;
    }

    public BigDecimal getValImpuesto() {
        return valImpuesto;
    }

    public void setValImpuesto(BigDecimal valImpuesto) {
        this.valImpuesto = valImpuesto;
    }

    public BigDecimal getValImppredial() {
        return valImppredial;
    }

    public void setValImppredial(BigDecimal valImppredial) {
        this.valImppredial = valImppredial;
    }

    public String getNomCodigocatastral() {
        return nomCodigocatastral;
    }

    public void setNomCodigocatastral(String nomCodigocatastral) {
        this.nomCodigocatastral = nomCodigocatastral;
    }

    public String getNomCodigocatastralanterior() {
        return nomCodigocatastralanterior;
    }

    public void setNomCodigocatastralanterior(String nomCodigocatastralanterior) {
        this.nomCodigocatastralanterior = nomCodigocatastralanterior;
    }

    public String getCodDpa() {
        return codDpa;
    }

    public void setCodDpa(String codDpa) {
        this.codDpa = codDpa;
    }

    public String getCodZona() {
        return codZona;
    }

    public void setCodZona(String codZona) {
        this.codZona = codZona;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    public String getCodManzana() {
        return codManzana;
    }

    public void setCodManzana(String codManzana) {
        this.codManzana = codManzana;
    }

    public String getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(String codPredio) {
        this.codPredio = codPredio;
    }

    public String getCodRegimentenencia() {
        return codRegimentenencia;
    }

    public void setCodRegimentenencia(String codRegimentenencia) {
        this.codRegimentenencia = codRegimentenencia;
    }

    public String getCodHorizontal() {
        return codHorizontal;
    }

    public void setCodHorizontal(String codHorizontal) {
        this.codHorizontal = codHorizontal;
    }

    public String getStsBarrio() {
        return stsBarrio;
    }

    public void setStsBarrio(String stsBarrio) {
        this.stsBarrio = stsBarrio;
    }

    public String getTxtDireccion() {
        return txtDireccion;
    }

    public void setTxtDireccion(String txtDireccion) {
        this.txtDireccion = txtDireccion;
    }

    public String getTxtObservacion() {
        return txtObservacion;
    }

    public void setTxtObservacion(String txtObservacion) {
        this.txtObservacion = txtObservacion;
    }

    public BigDecimal getValAreaPredio() {
        return valAreaPredio;
    }

    public void setValAreaPredio(BigDecimal valAreaPredio) {
        this.valAreaPredio = valAreaPredio;
    }

    public BigDecimal getValAreaFrente() {
        return valAreaFrente;
    }

    public void setValAreaFrente(BigDecimal valAreaFrente) {
        this.valAreaFrente = valAreaFrente;
    }

    public BigDecimal getValAreaFondo() {
        return valAreaFondo;
    }

    public void setValAreaFondo(BigDecimal valAreaFondo) {
        this.valAreaFondo = valAreaFondo;
    }

    public BigDecimal getValAreaConstruccion() {
        return valAreaConstruccion;
    }

    public void setValAreaConstruccion(BigDecimal valAreaConstruccion) {
        this.valAreaConstruccion = valAreaConstruccion;
    }

    public String getTxtNorte() {
        return txtNorte;
    }

    public void setTxtNorte(String txtNorte) {
        this.txtNorte = txtNorte;
    }

    public String getTxtSur() {
        return txtSur;
    }

    public void setTxtSur(String txtSur) {
        this.txtSur = txtSur;
    }

    public String getTxtEste() {
        return txtEste;
    }

    public void setTxtEste(String txtEste) {
        this.txtEste = txtEste;
    }

    public String getTxtOeste() {
        return txtOeste;
    }

    public void setTxtOeste(String txtOeste) {
        this.txtOeste = txtOeste;
    }

    public String getStsTenencia() {
        return stsTenencia;
    }

    public void setStsTenencia(String stsTenencia) {
        this.stsTenencia = stsTenencia;
    }

    public String getStsTenenciaotro() {
        return stsTenenciaotro;
    }

    public void setStsTenenciaotro(String stsTenenciaotro) {
        this.stsTenenciaotro = stsTenenciaotro;
    }

    public String getStsTransferenciadominio() {
        return stsTransferenciadominio;
    }

    public void setStsTransferenciadominio(String stsTransferenciadominio) {
        this.stsTransferenciadominio = stsTransferenciadominio;
    }

    public BigDecimal getValPredioAreaEscritura() {
        return valPredioAreaEscritura;
    }

    public void setValPredioAreaEscritura(BigDecimal valPredioAreaEscritura) {
        this.valPredioAreaEscritura = valPredioAreaEscritura;
    }

    public String getTxtNotaria() {
        return txtNotaria;
    }

    public void setTxtNotaria(String txtNotaria) {
        this.txtNotaria = txtNotaria;
    }

    public String getTxtCiudad() {
        return txtCiudad;
    }

    public void setTxtCiudad(String txtCiudad) {
        this.txtCiudad = txtCiudad;
    }

    public String getTxtRegistroNumero() {
        return txtRegistroNumero;
    }

    public void setTxtRegistroNumero(String txtRegistroNumero) {
        this.txtRegistroNumero = txtRegistroNumero;
    }

    public String getTxtDetalleRegistro() {
        return txtDetalleRegistro;
    }

    public void setTxtDetalleRegistro(String txtDetalleRegistro) {
        this.txtDetalleRegistro = txtDetalleRegistro;
    }

    public String getTxtInformante() {
        return txtInformante;
    }

    public void setTxtInformante(String txtInformante) {
        this.txtInformante = txtInformante;
    }

    public String getTxtInformanteRelacion() {
        return txtInformanteRelacion;
    }

    public void setTxtInformanteRelacion(String txtInformanteRelacion) {
        this.txtInformanteRelacion = txtInformanteRelacion;
    }

    public String getStsSituacion() {
        return stsSituacion;
    }

    public void setStsSituacion(String stsSituacion) {
        this.stsSituacion = stsSituacion;
    }

    public String getStsEscritura() {
        return stsEscritura;
    }

    public void setStsEscritura(String stsEscritura) {
        this.stsEscritura = stsEscritura;
    }

    public String getFecInscripcion() {
        return fecInscripcion;
    }

    public void setFecInscripcion(String fecInscripcion) {
        this.fecInscripcion = fecInscripcion;
    }

    public String getFecEscritura() {
        return fecEscritura;
    }

    public void setFecEscritura(String fecEscritura) {
        this.fecEscritura = fecEscritura;
    }

    public String getFecRegistro() {
        return fecRegistro;
    }

    public void setFecRegistro(String fecRegistro) {
        this.fecRegistro = fecRegistro;
    }

    public String getStsEstado() {
        return stsEstado;
    }

    public void setStsEstado(String stsEstado) {
        this.stsEstado = stsEstado;
    }

    public List<Terreno> getListaDescripcionTerreno() {
        return listaDescripcionTerreno;
    }

    public void setListaDescripcionTerreno(List<Terreno> listaDescripcionTerreno) {
        this.listaDescripcionTerreno = listaDescripcionTerreno;
    }

    public String getNomNumero() {
        return nomNumero;
    }

    public void setNomNumero(String nomNumero) {
        this.nomNumero = nomNumero;
    }

    public String getNomCartografia() {
        return nomCartografia;
    }

    public void setNomCartografia(String nomCartografia) {
        this.nomCartografia = nomCartografia;
    }

    public String getNomFotoAerea() {
        return nomFotoAerea;
    }

    public void setNomFotoAerea(String nomFotoAerea) {
        this.nomFotoAerea = nomFotoAerea;
    }

    public String getNomcCartografiaOtros() {
        return nomcCartografiaOtros;
    }

    public void setNomcCartografiaOtros(String nomcCartografiaOtros) {
        this.nomcCartografiaOtros = nomcCartografiaOtros;
    }

    public BigDecimal getValServiciosadministrativos() {
        return valServiciosadministrativos;
    }

    public String getTxtCallePrincipal() {
        return txtCallePrincipal;
    }

    public String getFecEmision() {
        return fecEmision;
    }

    public BigDecimal getValContruccionObsoleta() {
        return valContruccionObsoleta;
    }

    public void setValContruccionObsoleta(BigDecimal valContruccionObsoleta) {
        this.valContruccionObsoleta = valContruccionObsoleta;
    }

    public BigDecimal getValDescuentoExoneracion() {
        return valDescuentoExoneracion;
    }

    public void setValDescuentoExoneracion(BigDecimal valDescuentoExoneracion) {
        this.valDescuentoExoneracion = valDescuentoExoneracion;
    }

    public String getAnioEmision() {
        return anioEmision;
    }

    public void setAnioEmision(String anioEmision) {
        this.anioEmision = anioEmision;
    }

    public BigDecimal getValInteresaplicado() {
        return valInteresaplicado;
    }

    public void setValInteresaplicado(BigDecimal valInteresaplicado) {
        this.valInteresaplicado = valInteresaplicado;
    }

    public BigDecimal getValDescuentoaplicado() {
        return valDescuentoaplicado;
    }

    public void setValDescuentoaplicado(BigDecimal valDescuentoaplicado) {
        this.valDescuentoaplicado = valDescuentoaplicado;
    }

    public BigDecimal getValPagado() {
        return valPagado;
    }

    public void setValPagado(BigDecimal valPagado) {
        this.valPagado = valPagado;
    }

    public String getFecPago() {
        return fecPago;
    }

    public void setFecPago(String fecPago) {
        this.fecPago = fecPago;
    }

    public String getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(String contribuyente) {
        this.contribuyente = contribuyente;
    }

    public PresentacionFichaCatastralDto(FichaCatastralDto ficha) {
        Contribuyentes contr = ficha.getContribuyentePropiedad();
        Predios pred = ficha.getPredio();
        this.codCedularuc = contr.getCodCedularuc();
        this.nomApellidos = contr.getNomApellidos();
        this.nomNombres = contr.getNomNombres();
        this.nomCodigocatastral = pred.getNomCodigocatastral();
        this.codCatastral = pred.getCodCatastral().toString();
        this.codCampo = pred.getCodCampo();
    }

    public PresentacionFichaCatastralDto(Predios predio) {
        FichaCatastralDto fichaCatastralDto = new FichaCatastralDto(predio);
        setearDatosPredio(fichaCatastralDto.getPredio());
        setearDatosContribuyente(fichaCatastralDto.getContribuyentePropiedad());
        this.listaDescripcionTerreno = (List<Terreno>) fichaCatastralDto.getPredio().getCaracteristicasTerreno();
        this.listaBloques = (List<Bloques>) fichaCatastralDto.getPredio().getBloques();
        this.listaServicios = (List<Servicios>) fichaCatastralDto.getPredio().getServicios();
        setearDatosPropiedad(fichaCatastralDto.getPropiedad());

    }

    private void setearDatosLogPredio(LogPredio logPredio) {
        this.codUsu = logPredio.getCodUsu();
        this.fecLog = generarHora(logPredio.getFecLog());
        this.nomIp = logPredio.getNomIp();
        this.txtLog = logPredio.getTxtLog();
        this.logAccion = logPredio.getLogAccion().getAccion();

    }

    public PresentacionFichaCatastralDto(LogPredio logPredio) {
        FichaCatastralDto fichaCatastralDto = new FichaCatastralDto(logPredio.getCodCatastral());
        setearDatosContribuyente(fichaCatastralDto.getContribuyentePropiedad());
        setearDatosLogPredio(logPredio);
        this.nomCodigocatastral = fichaCatastralDto.getPredio().getNomCodigocatastral();

    }

    public PresentacionFichaCatastralDto(Titulos titulo) {
        FichaCatastralDto fichaCatastralDto = new FichaCatastralDto(titulo.getCodCatastral());
        setearDatosTitulo(titulo);
        setearDatosContribuyente(fichaCatastralDto.getContribuyentePropiedad());
        setearDatosPropiedad(fichaCatastralDto.getPropiedad());
        setearDatosPredioTitulo(titulo.getCodCatastral());
    }

    public PresentacionFichaCatastralDto(Titulos titulo, Boolean opcion) {
        FichaCatastralDto fichaCatastralDto = new FichaCatastralDto(titulo.getCodCatastral());
        setearDatosTitulo(titulo);
        setearDatosMovimientoTitulo(titulo);
        setearDatosContribuyente(fichaCatastralDto.getContribuyentePropiedad());
        setearDatosPropiedad(fichaCatastralDto.getPropiedad());
        setearDatosPredioTitulo(titulo.getCodCatastral());
    }

    private void setearDatosPredioTitulo(Predios predio) {
        //this.txtCallePrincipal = predio.getTxtDireccion();
        //this.stsBarrio = predio.getStsBarrio();
        this.nomNumero = predio.getNomNumero();
        this.nomCodigocatastral = predio.getNomCodigocatastral();
    }

    private void setearDatosPredio(Predios predio) {
        this.codDpa = predio.getCodDpa();
        this.codHorizontal = predio.getCodHorizontal();
        this.codManzana = predio.getCodManzana();
        this.codPredio = predio.getCodPredio();
        this.codRegimentenencia = predio.getCodRegimentenencia();
        this.codSector = predio.getCodSector();
        this.codZona = predio.getCodZona();
        this.nomCodigocatastral = predio.getNomCodigocatastral();
        this.nomCodigocatastralanterior = predio.getNomCodigocatastralanterior();
        this.stsBarrio = predio.getStsBarrio();
        this.stsTipo = predio.getStsTipo();
        this.txtDireccion = predio.getTxtDireccion();
        this.txtObservacion = predio.getTxtObservacion();
        this.txtEste = predio.getTxtEste();
        this.txtNorte = predio.getTxtNorte();
        this.txtOeste = predio.getTxtOeste();
        this.txtSur = predio.getTxtSur();
        this.valAreaPredio = predio.getValAreaPredio();
        this.valAreaFrente = predio.getValAreaFrente();
        this.valAreaFondo = predio.getValAreaFondo();
        this.valAreaConstruccion = predio.getValAreaConstruccion();
        this.nomNumero = predio.getNomNumero();
        this.nomCartografia = predio.getNomCartografia();
        this.nomFotoAerea = predio.getNomFotoAerea();
        this.nomcCartografiaOtros = predio.getNomcCartografiaOtros();
        this.valCoordenadaEste = predio.getValCoordenadaEste();
        this.valCoordenadaNorte = predio.getValCoordenadaNorte();
        this.valTerreno = predio.getValTerreno();
        this.valEdifica = predio.getValEdifica();
        this.valPredio = predio.getValPredio();
        this.valCem = predio.getValCem();
        this.valBomberos = predio.getValBomberos();
        this.valEmision = predio.getValEmision();
        this.valNoEdificacion = predio.getValNoEdificacion();
        this.valAmbientales = predio.getValAmbientales();
        this.valImpuesto = predio.getValImpuesto();
        this.valImppredial = predio.getValImppredial();
    }

    private void setearDatosContribuyente(Contribuyentes contribuyentePropiedad) {
        this.codCedularuc = contribuyentePropiedad.getCodCedularuc();
        this.nomApellidos = contribuyentePropiedad.getNomApellidos();
        this.nomNombres = contribuyentePropiedad.getNomNombres();
        this.contribuyente = contribuyentePropiedad.getNomApellidos().trim() + ' ' + contribuyentePropiedad.getNomNombres().trim();
        this.txtTelefono = contribuyentePropiedad.getTxtTelefono();
        this.txtDireccion = contribuyentePropiedad.getTxtDireccion();
        this.txtEmail = contribuyentePropiedad.getTxtEmail();
        this.nomCiudadDomicilio = contribuyentePropiedad.getNomCiudadDomicilio();
        this.nombreRepresentante = contribuyentePropiedad.getNombreRepresentante();
        this.cedulaRepresentante = contribuyentePropiedad.getCedulaRepresentante();
        this.direccionRepresentante = contribuyentePropiedad.getDireccionRepresentante();
    }

    private void setearDatosPropiedad(Propiedad propiedad) {
        this.stsTenencia = propiedad.getStsTenencia().getStsTenencia();
        this.stsTenenciaotro = propiedad.getStsTenenciaotro();
        this.stsTransferenciadominio = propiedad.getStsTransferenciadominio().getStsTransferenciadominio();
    }

    public String generarHora(Date fechaEmision) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        //return format.format(fechaEmision);
        return !ComunUtil.esNulo(fechaEmision) ? format.format(fechaEmision) : null;
    }

    public String generarAnio(Date fechaEmision) {
        DateFormat format = new SimpleDateFormat("yyyy");
        return format.format(fechaEmision);
    }

    private void setearDatosMovimientoTitulo(Titulos titulo) {
        List<TituloMovimientos> movimientos = titulo.getListaMovimientosTitulo();
        TituloMovimientos aux = null;
        if (!ComunUtil.esNulo(movimientos) || movimientos.isEmpty()) {
            for (TituloMovimientos movimiento : movimientos) {
                if (movimiento.getEstadoTitulo().equals(EnumEstadoTitulo.TITULO_DESMARCADO)) {
                    aux = movimiento;
                }
            }
            this.razonMovimiento = !ComunUtil.esNulo(aux) && !ComunUtil.esNulo(aux.getRazonMovimiento()) ? aux.getRazonMovimiento() : "No ha sido especificada";
        }
    }

    private void setearValoresDescRec(BigDecimal descuentoAplicado) {
        if (!ComunUtil.esNulo(descuentoAplicado)) {
            switch (descuentoAplicado.signum()) {
                case -1:
                    this.valRecargoPago = descuentoAplicado.negate();
                    this.valDescuentoPago = BigDecimal.ZERO;
                    break;
                case 1:
                    this.valDescuentoPago = descuentoAplicado.negate();
                    this.valRecargoPago = BigDecimal.ZERO;
                    break;
                default:
                    this.valDescuentoPago = BigDecimal.ZERO;
                    this.valRecargoPago = BigDecimal.ZERO;
                    break;
            }
        }
    }

    private void setearDatosTitulo(Titulos titulo) {
        this.valAreaPredio = titulo.getValAreaterreno();
        this.valAreaConstruccion = titulo.getValAreaconstruccion();
        this.valTerreno = titulo.getValValorterreno();
        this.valEdifica = titulo.getValConstruccion();
        this.valPredio = titulo.getValBaseimponible();
        this.valImppredial = titulo.getValTotalapagar();
        this.valBomberos = titulo.getValBomberos();
        this.valCem = titulo.getValCem();
        this.valNoEdificacion = titulo.getValNoconstruido();
        this.valImpuesto = titulo.getValImpuestopredial();
        this.fecEmision = generarHora(titulo.getFecEmision());
        this.valServiciosadministrativos = titulo.getValServiciosadministrativos();
        this.valContruccionObsoleta = titulo.getValContruccionObsoleta();
        this.valDescuentoExoneracion = titulo.getValDescuentoExoneracion();
        this.anioEmision = generarAnio(titulo.getFecEmision());
        this.nomCodigocatastral = titulo.getNomCodigocatastral();
        this.valDescuentoaplicado = titulo.getValDescuentoaplicado();
        this.valInteresaplicado = titulo.getValInteresaplicado();
        this.valPagado = titulo.getValPagado();
        this.txtCallePrincipal = titulo.getTxtDireccion();
        this.stsBarrio = titulo.getTxtBarrio();
        this.fecPago = generarHora(titulo.getFecFpago());
        this.codigoTitulo = titulo.getCodSecuencial();
        setearValoresDescRec(titulo.getValDescuentoaplicado());
    }

}
