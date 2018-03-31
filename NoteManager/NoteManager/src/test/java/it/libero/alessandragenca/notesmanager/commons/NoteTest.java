package it.libero.alessandragenca.notesmanager.commons;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;


public class NoteTest {
    Note n;

    @Before
    public void setUp() throws Exception {
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2017, GregorianCalendar.SEPTEMBER, 27);
        Date data = gregorianCalendar1.getTime();
        n= new Note ( "titolo test", "testo test",data);
        Assert.assertNotNull("Creato oggetto nota", n);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTitle() {
        n.getTitle();
        Assert.assertEquals(" Errore Test del get Title", n.getTitle(), "titolo test");



    }

    @Test
    public void getText() {

        Assert.assertEquals(" Errore Test del get Text", n.getText(), "testo test");
    }

    @Test
    public void getDate() {
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2017, GregorianCalendar.SEPTEMBER, 27);
        Date data = gregorianCalendar1.getTime();

        Assert.assertEquals(" Errore Test del get Date", n.getDate(),data);
    }

    @Test
    public void setTitle() {
        String title= n.getTitle();
        n.setTitle("Titolo modificato");
        Assert.assertNotEquals(" Errore Test modifica testo",n.getTitle(),title);
    }

    @Test
    public void setText() {
        String text= n.getText();
        n.setText("Testo modificato");
        Assert.assertNotEquals(" Errore Test modifica testo",n.getText(),text);
    }

    @Test
    public void setDate() {
        Date d= n.getDate();
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2017, GregorianCalendar.NOVEMBER, 12);
        Date data1 = gregorianCalendar1.getTime();
        n.setDate(data1);
        Assert.assertNotEquals(" Errore  Test modifica data",n.getDate(),d);


    }

    @Test
    public void TestToString() {
       String test= "Note [Title=" + n.getTitle()+ ", Text=" + n.getText() + ", Date=" + n.getDate().toString() + "]";

        Assert.assertEquals("Errore nel ToString",  n.toString(), test);

    }
}