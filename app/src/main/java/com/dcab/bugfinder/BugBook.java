package com.dcab.bugfinder;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BugBook extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_insetti);

        listaInsetti = new ArrayList<BugUIBean>();

        listaInsetti.add(new BugUIBean("Morimo scabroso/funereo", "Cerambycidae", "Morimus asper/funereus è una specie di grandi dimensioni e robusta (lunghezza totale: 16-38" +
                        "mm), con corpo ovale; le elitre sono saldate tra di loro, hanno un aspetto granuloso e presentano ognuna due" +
                        "macchie nere. Solo M. funereus è incluso nell'allegato II della Direttiva Habitat, ma recenti studi hanno" +
                        "evidenziato che questo coleottero appartiene alla stessa specie di M. asper. Per questo motivo, entrambe le" +
                        "specie sono qui considerate come M. asper/funereus, che mostra una notevole variabilità cromatica. Morimus" +
                        "asper ha un colore di fondo più scuro e quasi nero, mentre M. funereus è più chiaro con macchie più evidenti." +
                        "Morimus funereus è presente esclusivamente nella parte orientale del Friuli Venezia Giulia (cfr. mappa), mentre" +
                        "M. asper è presente nel resto d’Italia. I maschi di entrambe le specie possiedono antenne molto più lunghe del" +
                        "corpo, mentre nelle femmine raggiungono circa l’apice delle elitre. Morimus asper/funereus può essere confusa" +
                        "con Herophila tristis e Lamia textor ma in queste specie il terzo antennomero è più corto del primo o leggermente" +
                        "più lungo, mentre in M. asper è palesemente più lungo.", "Coleopteri", "Nord Italia", "Non so che difese poter attuare!", R.drawable.morimo_scabroso_funereo));
        listaInsetti.add(new BugUIBean("Cervo volante", "Lucanidae", ". È il più grande coleottero europeo: i maschi possono infatti misurare fino a 89 mm. Il colore di" +
                        "fondo degli esemplari varia dal bruno-rossiccio al bruno scuro, quasi nero. I maschi, a differenza delle femmine," +
                        "sono caratterizzati da mandibole molto sviluppate che ricordano le corna di un cervo (da cui deriva il suo nome" +
                        "comune). Lucanus cervus può essere talvolta confuso con Lucanus tetraodon, specie di dimensioni minori e" +
                        "presente nelle regioni meridionali (con isolate segnalazioni in Lombardia, Liguria ed Emilia-Romagna); le due" +
                        "specie possono convivere nelle regioni centrali, dove possono trovarsi individui di difficile identificazione. Dorcus" +
                        "parallelipipedus (Coleottero Lucanide) e le femmine di Oryctes nasicornis (Coleottero Dynastidae), sono altre" +
                        "due entità con le quali è possibile confondere le femmine di L. cervus.", "Coleoptera", "Nord Italia", "Non so che difese poter attuare!", R.drawable.cervo_volante));
        listaInsetti.add(new BugUIBean("Argynnis", "Nymphalidae", "Specie di medie dimensioni, con lunghezza dell’ala anteriore di 22-27 mm. Maschio e femmina" +
                        "sono simili, la femmina di dimensioni leggermente maggiori e di colore più chiaro. Dorsalmente la colorazione di" +
                        "fondo è arancione, con ali lievemente spolverate di grigio alla base, e provviste su gran parte della superficie" +
                        "alare di piccole macchie nere diversamente conformate. I margini sono bordati di nero in modo ben definito." +
                        "Ventralmente le ali anteriori hanno una colorazione di fondo arancione, con macchie nere ed apici verdastri" +
                        "contenenti spazi bianchi. Le ali posteriori sono di colore giallastro sfumato di verde, con numerose piccole" +
                        "macchie bianco-argenteo. La specie potrebbe essere confusa con specie dello stesso genere, dalle quali si distingue per la combinazione" +
                        "di diversi caratteri: sul lato superiore la colorazione di fondo è arancione in entrambi i sessi; gli elementi scuri del" +
                        "disegno sono poco marcati, e non originano mai linee ondulate continue; sul lato inferiore delle ali posteriori la" +
                        "colorazione di fondo non è mai uniformemente giallastra o verde, ma variegata, con piccole macchie biancoargentee e rossicce su fondo giallo-verdastro.", "Lepidoptera", "Sardegna", "Non so che difese poter attuare!", R.drawable.argynnis));
        listaInsetti.add(new BugUIBean("Arge", "Nymphalidae", "Specie di dimensioni medio-grandi, con lunghezza dell’ala anteriore di 25-30 mm. Maschio e" +
                        "femmina sono simili, la femmina di dimensioni leggermente maggiori e con disegni neri meno estesi." +
                        "Dorsalmente la colorazione di fondo è bianca, con un reticolo di linee, macchie nere e ocelli scuri, disposti" +
                        "secondo un disegno ben definito. Ventralmente la colorazione di fondo è bianca, con sottili linee nere o marroni" +
                        "che sottolineano l'andamento delle nervature e degli ocelli, che presentano una pupilla azzurra." +
                        "La specie potrebbe essere confusa con le congeneri, da cui si distingue per diversi caratteri: i disegni sono" +
                        "generalmente più chiari; sulla pagina superiore dell’ala anteriore il tratto nero che attraversa la cella è" +
                        "incompleto, ovvero non tocca la nervatura inferiore. Inoltre, in prossimità dell’apice dell’ala anteriore, sono" +
                        "presenti due ocelli con pupilla azzurro-blu distintivi. Sulla pagina inferiore dell’ala anteriore i disegni sono poco" +
                        "marcati, le linee a zigzag marginali sono ridotte e formano angoli retti. Sulla pagina inferiore dell’ala posteriore gli" +
                        "ocelli sono molto evidenti, brillanti e con contorni neri ben distinti, e le nervature sono marcate di marrone scuro" +
                        "o nero.", "Lepidoptera", "Centro - Sud Italia", "Non so che difese poter attuare!", R.drawable.arge));
        listaInsetti.add(new BugUIBean("Mnemosine", "Papilionidae", "Parnassius mnemosyne è una farfalla di dimensioni medie (apertura alare: 50 - 70 mm circa)," +
                        "con ali di forma arrotondata e di color bianco con venature nere ben evidenziate. Le ali posteriori hanno due" +
                        "evidenti macchie nere all’interno della cellula discale, mentre gli apici sono traslucidi, quasi completamente" +
                        "trasparenti. Il margine interno (anale) delle ali posteriori porta invece una banda di squame nere o grigio scure" +
                        "più o meno estesa che può arrivare a toccare la cellula discale e la macchia nera all’apice distale di quest’ultima." +
                        "Lo stesso pattern è presente sulla faccia ventrale delle ali. Le femmine possono avere macchie nere ed aree" +
                        "grigie più estese dei maschi. La specie è simile alle altre specie del genere Parnassius presenti in Italia ma è" +
                        "facilmente distinguibile da queste per la totale assenza di macchie rosse sulle ali. La specie può anche essere" +
                        "confusa con Aporia crataegi (Linnaeus, 1758) che però non presenta le macchie scure sulle ali ed ha l’apice" +
                        "delle antenne chiaro. La larva è di colore nero (a volte bruno scuro) con una serie di macchie dorso-laterali di" +
                        "dimensioni e forma irregolare di colore arancione più o meno intenso; il corpo è ricoperto da corte setole nere.", "Lepidoptera", "Montagne d'Italia", "Non so che difese poter attuare!", R.drawable.mnemosine));
        listaInsetti.add(new BugUIBean("Guardaruscello", "Cordulegastridae", "Specie grossa e robusta (lunghezza totale di 73-79 mm, lunghezza dell’addome di 55-63 mm). Come" +
                        "tutte le specie del genere Cordulegaster, gli adulti sono neri con disegni gialli e gli occhi si toccano in un solo punto." +
                        "In C. trinacriae il capo è giallo e nero con occhi verdi e l’addome presenta disegni ridotti. Un altro carattere tipico è" +
                        "la forma delle appendici addominali; quelli superiori sono lunghe, sottili e sinuose, mentre l’appendice inferiore è" +
                        "profondamente intagliata a forma di V (angolo 70°-130°). La specie può essere confusa con le specie congeneri" +
                        "presenti in Italia, dalle quali può essere distinta per le caratteristiche delle appendici addominali: mentre in C." +
                        "bidentata le appendici superiori portano due denti ventrali, in C. trinacriae ne è presente solo uno. In C. boltonii" +
                        "invece le appendici superiori sono corte e dritte, e la forma dell’appendice inferiore è dritta o al massimo" +
                        "leggermente intagliata. Un altro carattere tipico per C. trinacriae è l’assenza di anelli gialli nella parte terminale degli" +
                        "uriti 7 e 8, a differenza di C. boltonii , in cui invece sono presenti.", "Odonata", "Sud Italia", "Non so che difese poter attuare!", R.drawable.guardaruscello));
        listaInsetti.add(new BugUIBean("Smeralda di fiume", "Corduliidae", "Specie di taglia medio grande (lunghezza totale di 47-54 mm, lunghezza dell’addome di 33-39" +
                        "mm). La colorazione dominante è verde metallica: il torace e l’addome presentano una colorazione variabile tra il" +
                        "marrone e il verde smeraldo. Sono presenti macchie giallastre sul capo e sul torace, e peculiari macchie gialle" +
                        "dorsali sull’addome. Quest’ultimo è molto stretto alla base e si allarga progressivamente fino all’estremità. Le ali" +
                        "posteriori presentano due caratteristiche venature oblique. Le femmine, di dimensioni leggermente minori dei" +
                        "maschi, presentano ali di tonalità giallastra, soprattutto nella porzione basale. È riconoscibile anche per la" +
                        "postura in volo, con l’addome arcuato verso l’alto. E’ possibile confondere questa specie con altri Corduliidae," +
                        "particolarmente con Somatochlora metallica, con cui spesso condivide lo stesso ambiente, ma può essere" +
                        "facilmente distinta grazie al colore verde brillante degli occhi e alle macchie gialle dorsali.", "Odonata", "Nord-Ovest Italia", "Non so che difese poter attuare!", R.drawable.smeralda_di_fiume));
        listaInsetti.add(new BugUIBean("Brachytrupes megacephalus", "Gryllidae", "Brachytrupes megacephalus è un grillo inconfondibile, per la sua grossa taglia (31-38 mm per i" +
                        "maschi, 33-40 mm per le femmine) e per il caratteristico capo più largo della larghezza del torace. Il colore è" +
                        "tendente al giallastro con macchie brune, ed ali brunastre. I cerci sono molto lunghi mentre, nelle femmine," +
                        "l’ovopositore è relativamente corto (3-4 mm).", "Orthoptera", "Sicilia - Sardegna", "Non so che difese poter attuare!", R.drawable.brachytrupes_megacephalus));
        listaInsetti.add(new BugUIBean("Cavalletta stregona", "Tettigoniidae", "Saga pedo, con i suoi 100-120 mm di lunghezza (ovopositore compreso) è il più grande" +
                        "ortottero europeo. La specie è caratterizzata da zampe anteriori (e mediane) di tipo raptatorio, munite di doppia" +
                        "fila di denti su tibia e femore, e da una livrea del corpo generalmente verde (alcuni esemplari possono essere di" +
                        "colore bruno) con linea longitudinale bianca o giallastra che corre dal torace all’estremità posteriore dell’addome." +
                        "Le femmine sono prive di ali e possiedono un robusto e lungo (31-41 mm) ovopositore, leggermente incurvato" +
                        "verso l’alto. Il maschio è estremamente raro (un esemplare trovato in Svizzera) e possiede ali molto ridotte, che" +
                        "non consentono il volo.", "Orthoptera", "Italia", "Non so che difese poter attuare!", R.drawable.cavalletta_stregona));

        registroInsetti = findViewById(R.id.registroInsetti);
        leftArrow = findViewById(R.id.left_arrow);

        bugBookAdapter = new BugBookAdapter(getApplicationContext(), R.layout.insetto_layout, listaInsetti);

        registroInsetti.setAdapter(bugBookAdapter);

        registroInsetti.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                parent.getItemAtPosition(position);
                showBug(position);
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }


    public void showBug(int position)
    {
        final Dialog dialog = new Dialog(BugBook.this);
        dialog.setContentView(R.layout.insetto_page);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setTitle("");
        dialog.setCancelable(true);

        bugTypeFULL = dialog.findViewById(R.id.bugTypeFULL);
        bugName = dialog.findViewById(R.id.bugName);
        bugOrdine = dialog.findViewById(R.id.bugOrdine);
        bugProvenienza = dialog.findViewById(R.id.bugProvenienza);
        bugDescription = dialog.findViewById(R.id.bugDescription);
        bugDifese = dialog.findViewById(R.id.bugDifese);
        bugLogo = dialog.findViewById(R.id.logoIns);

        bugTypeFULL.setText(listaInsetti.get(position).getType());
        bugName.setText(listaInsetti.get(position).getNome());
        bugOrdine.setText(listaInsetti.get(position).getOrdine());
        bugProvenienza.setText(listaInsetti.get(position).getProvenienza());
        bugDescription.setText(listaInsetti.get(position).getDescription());
        bugDifese.setText(listaInsetti.get(position).getDifese());
        bugLogo.setImageResource(listaInsetti.get(position).getImageId());

        //set up button
        ImageView backArrow = (ImageView) dialog.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it
        dialog.show();
    }


    private ListView registroInsetti;
    private TextView bugTypeFULL, bugName, bugOrdine, bugProvenienza, bugDescription, bugDifese;
    private ImageView leftArrow, bugLogo;;
    private BugBookAdapter bugBookAdapter;
    private ArrayList<BugUIBean> listaInsetti;

}
