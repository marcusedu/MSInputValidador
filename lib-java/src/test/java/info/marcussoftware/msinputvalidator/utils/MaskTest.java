package info.marcussoftware.msinputvalidator.utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 13/06/2018.
 */
public class MaskTest {

    @Test
    public void deveFormatarCep() {
        Mask mask = new Mask("#####-###");
        Assert.assertEquals("35701-303", mask.applyMask("35701303"));
    }

    @Test
    public void deveFormatarParteDoCEP() {
        Mask mask = new Mask("#####-###");
        Assert.assertEquals("35701-", mask.applyMask("35701"));
    }

    @Test
    public void deveFormatarExcedenteDoCEP() {
        Mask mask = new Mask("#####-###");
        Assert.assertEquals("35701-3037", mask.applyMask("357013037"));
    }

    @Test
    public void deveFormatarCelular() {
        Mask mask = new Mask("(##)### ### ###");
        Assert.assertEquals("(31)996 589 123", mask.applyMask("31996589123"));
    }

    @Test
    public void deveRemoverFormatacaoCelular() {
        Mask mask = new Mask("(##)### ### ###");
        Assert.assertEquals("31996589123", mask.removeMask("(31)996 589 123"));
    }

    @Test
    public void deveFormatarCelularParcial() {
        Mask mask = new Mask("(##)### ### ###");
        Assert.assertEquals("(31)996 589 ", mask.applyMask("31996589"));
    }

    @Test
    public void deveFormatarCelularExedente() {
        Mask mask = new Mask("(##)### ### ###");
        Assert.assertEquals("(31)996 589 12374", mask.applyMask("3199658912374"));
    }

    @Test
    public void deveFormatarCPF() {
        Mask mask = new Mask("###.###.###-##");
        Assert.assertEquals("093.063.916-25", mask.applyMask("09306391625"));
    }

    @Test
    public void deveFormatarCPFParcial() {
        Mask mask = new Mask("###.###.###-##");
        Assert.assertEquals("093.063.", mask.applyMask("093063"));
    }

    @Test
    public void deveFormatarCPFExcedente() {
        Mask mask = new Mask("###.###.###-##");
        Assert.assertEquals("093.063.916-25123", mask.applyMask("09306391625123"));
    }

    @Test
    public void deveFormatarCNPJ() {
        Mask mask = new Mask("##.###.###/####-##");
        Assert.assertEquals("12.345.678/1000-99", mask.applyMask("12345678100099"));
    }

    @Test
    public void deveFormatarMoeda() {
        Mask mask = new Mask("R$ #.###,##");
        Assert.assertEquals("R$ 5.450,77", mask.applyMask("545077"));
    }

    @Test
    public void deveFormatarMoedaParcial() {
        Mask mask = new Mask("R$ #.###,##");
        Assert.assertEquals("R$ 450,77", mask.applyMask("45077"));
    }

    @Test
    public void deveFormatarMoedaExedente() {
        Mask mask = new Mask("R$ #.###,##");
        Assert.assertEquals("R$ 1.056.450,77", mask.applyMask("105645077"));
    }

    @Test
    public void deveFormatarMoedaMuitoGrande() {
        Mask mask = new Mask("R$ #.###,##");
        Assert.assertEquals("R$ 123.456.789.123.456.789,00", mask.applyMask("12345678912345678900"));
    }

    @Test
    public void deveRemoverFormatacaoMoeda() {
        Mask mask = new Mask("R$ #.###,##");
        Assert.assertEquals("12345678912345678900", mask.removeMask("R$ 123.456.789.123.456.789,00"));
    }

    @Test
    public void deveFormatarMoedaCentavos1() {
        Mask mask = new Mask("R$ #.###,##");
        Assert.assertEquals("R$ 0,55", mask.applyMask("55"));
    }

    @Test
    public void deveFormatarMoedaCentavos2() {
        Mask mask = new Mask("R$ #.###,##");
        Assert.assertEquals("R$ 0,07", mask.applyMask("07"));
    }

    @Test
    public void deveEscapar() {
        Mask mask = new Mask("###-####");
        Assert.assertEquals("A#D-1234", mask.applyMask("A#D1234"));
    }

    @Test
    public void deveEscaparParcial() {
        Mask mask = new Mask("###-####");
        Assert.assertEquals("A#D-12", mask.applyMask("A#D12"));
    }

    @Test
    public void deveEscaparExedente() {
        Mask mask = new Mask("###-####");
        Assert.assertEquals("A#D-123456", mask.applyMask("A#D123456"));
    }
}