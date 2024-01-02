//Gia Lee 20231785
package com.example.guessmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.*;

import java.util.Random;

import java.util.Scanner;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class GuessMaster extends AppCompatActivity {

    //View compondents
    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText userIn;
    private Button btnclearContent;
    private String user_input;
    private ImageView entityImage;
    String answer;

    private TextView example;

    public GuessMaster() {
        numOfEntities = 0;
        entities = new Entity[10];
    }

    //entity objects
    Country usa= new Country("United States", new Date("July", 4, 1776), "Washingston DC", 0.1);
    Person myCreator= new Person("myCreator", new Date("May", 6, 1800), "Male", 1);
    Politician trudeau = new Politician("Justin Trudeau", new Date("December",25,1971),"Male", "Liberal", 0.25);
    Singer dion= new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November",6,1981),0.5);
//    final GuessMaster gm = new GuessMaster();


    //instances and variables hehe
    private Entity[] entities ;
    private int numOfEntities;
    private Entity currentEntity;



    private int[] tickets= new int[100];
    private int numOfTickets;
    //Stores Entity Name
    String entName;
    int entityid = 0;
    int currentTicketWon = 0;
    int totaltickets= 0;

    private Random random = new Random(); //??? well see ig

    public void playGame(int entityId) {
        Entity entity = entities[entityId];
//        welcomeToGame(entity);
        playGame(entity);
    }
    public void welcomeToGame (Entity entity){
        AlertDialog.Builder welcomealert = new AlertDialog.Builder(GuessMaster.this);
        welcomealert.setTitle("GuessMaster_Game_v3");
        welcomealert.setMessage(entity.welcomeMessage());
        welcomealert.setCancelable(false);

        welcomealert.setNegativeButton("Start Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialop, int which) {
                int awardedTicketNumber = entity.getAwardedTicketNumber();
                String message = "Game is Starting...Enjoy\nAwarded Ticket Number: 0";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        //show dialog
        AlertDialog dialog = welcomealert.create();
        dialog.show();
    }


    public void addEntity(Entity entity) {
        entities[numOfEntities++] = entity.clone();
    }

    //why do i need this???


    public void playGame() {
        int entityId = genRandomEntityId();
        playGame(entityId);

    }

    //
    public int genRandomEntityId() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(numOfEntities);
    }


    //changeEntity method
    public void changeEntity() {
        entityid = genRandomEntityId();
        Entity entity = entities[entityid];
        entityName.setText(entity.getName());
        ImageSetter(entity);
    }

    //Image setter method

    private void ImageSetter(Entity entity) {
        if (entity instanceof Country) {
            entityImage.setImageResource(R.drawable.usaflag);
        } else if (entity instanceof Politician) {
            entityImage.setImageResource(R.drawable.justint);
        } else if (entity instanceof Singer) {
            entityImage.setImageResource(R.drawable.celidion);
        } else if (entity instanceof Person) {
            entityImage.setImageResource(R.drawable.creator);
        }
    }
//    public void ImageSetter(int index) {
//        switch (index) {
//            case 1:
//                entityImage.setImageResource(R.drawable.usaflag);
//                break;
//            case 0:
//                entityImage.setImageResource(R.drawable.justint);
//                break;
//            case 2:
//                entityImage.setImageResource(R.drawable.celidion);
//                break;
//            case 3:
//                entityImage.setImageResource(R.drawable.creator);
//                break;
//        }
//    }

    public void ContinueGame ( ) {
        entityid = genRandomEntityId();
        Entity entity = entities[entityid];
//        welcomeToGame(entity);
        entName = entity.getName();
        // Call the Image Setter method
        ImageSetter(entity);

        // Print the name of the entity to be guessed
        // in the entityName text view
        entityName.setText(entName);

        // C l e a r P r e v i o u s Entry
        userIn.getText().clear();


    }

        public void playGame(Entity entity) {

            /// replace for the scanner thingy
            entityName.setText(entity.getName());
            String answer = userIn.getText().toString();
            answer = answer.replace("\n", "").replace("\r", "");
            Date date = new Date(answer);

            // for the two cases if preced vs if not
            if (date.precedes(entity.getBorn())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Incorrect.");
                builder.setMessage("Try a later date.");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            } else if (entity.getBorn().precedes(date)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Incorrect.");
                builder.setMessage("Try an earlier date.");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            } else {
                tickets[numOfTickets++] = entity.getAwardedTicketNumber();
                for (int i = 0; i < 100; i++){
                    currentTicketWon = currentTicketWon + tickets[i];
                }

                int awardedTickets = currentEntity.getAwardedTicketNumber();
                totaltickets = totaltickets + currentEntity.getAwardedTicketNumber();
                ticketsum.setText("Total tickets earned: " + totaltickets);
//                ticketsum.setText(currentEntity.getAwardedTicketNumber());


//                ticketsum = currentEntity.getAwardedTicketNumber();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("You won");
                Toast.makeText(getBaseContext(), "BINGO! " + entity.closingMessage() + "Award Ticket Number:" + awardedTickets, Toast.LENGTH_SHORT).show();
//                builder.setMessage(String.format("Bingo!" + entity.closingMessage()));


                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ContinueGame();
                    }
                });
                builder.show();
            }
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //specify view components
        guessButton = (Button) findViewById(R.id.btnGuess);
        btnclearContent = (Button) findViewById(R.id.btnClear);
        entityImage = (ImageView) findViewById(R.id.entityImage);
        entityName = (TextView) findViewById(R.id.entityName);
        ticketsum = (TextView) findViewById(R.id.ticket);
        userIn = (EditText) findViewById(R.id.guessinput);
        example = (TextView)findViewById(R.id.dateExample);

        addEntity(trudeau);
        addEntity(dion);
        addEntity(usa);
        addEntity(myCreator);

        currentEntity = entities[random.nextInt(numOfEntities)];
        entityName.setText(currentEntity.getName());
        ImageSetter(currentEntity);
        welcomeToGame(currentEntity);

        // Define OnClickListener for clear button
        btnclearContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeEntity();
            }
        });

        // Define OnClickListener for submit button
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame(entityid);
            }
        });

    }
}