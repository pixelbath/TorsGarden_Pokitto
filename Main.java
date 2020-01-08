import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Castpixel16;
import femto.font.TIC80;

public enum GardenState{
    TITLE,
    MAINMENU,
    RUNNING,
    PAUSEMENU
}

class Main extends State {

    
    GardenState gardenState = GardenState.TITLE;
    
    HiRes16Color screen; // the screenmode we want to draw with

    Dog dog; // an animated sprite imported from Aseprite
    Bg background; // static image
    Title titleImage;

    //TITLE UPDATE
    int c1, c2, titleCursor;
    void titleInit(){
        titleImage = new Title();
        titleCursor = 0;
        c1 = 15;
        c2 = 15;
    }
    void titleUpdate(){
        //reset indicies
        c1 = 15;
        c2 = 15;
        if(Button.Up.isPressed()){
            dog.y -= 1;
        }
        if(Button.Down.isPressed()){
            dog.y += 1;
        }
        if(Button.Left.isPressed()){
            dog.setMirrored(true);
            dog.x -= 1;
        }
        if(Button.Right.isPressed()){
            dog.setMirrored(false);
            dog.x += 1;
        }
        
        if(dog.x+8 >= 80 && dog.x <= 100  ){
            if(dog.y+16 >= 100 && dog.y+16 <= 109){
                c1 = 9;
                titleCursor = 0;
            }
            if(dog.y+16 >= 110 && dog.y+16 <= 119){
                c2 = 9;
                titleCursor = 1;
            }
        }
        
        
        titleImage.draw(screen, 45, 10);
        
        screen.setTextColor(5);
        screen.setTextPosition(81, 101);
        screen.print("Start");
        screen.setTextColor(c1);
        screen.setTextPosition(80, 100);
        screen.print("Start");
        
        screen.setTextColor(5);
        screen.setTextPosition(81, 111);
        screen.print("Clear Save");
        screen.setTextColor(c2);
        screen.setTextPosition(80, 110);
        screen.print("Clear Save");
        
        
        switch(titleCursor){
            case 0:
                if(Button.A.justPressed()){
                    gardenState = GardenState.MAINMENU;
                }
                break;
            case 1:
                if(Button.A.justPressed()){
                    //gardenState = GardenState.MAINMENU;
                }
                break;
        }
        dog.draw(screen);
    }




    // start the game using Main as the initial state
    // and TIC80 as the menu's font
    public static void main(String[] args){
        Game.run( TIC80.font(), new Main() );
    }
    
    // Avoid allocation in a State's constructor.
    // Allocate on init instead.
    void init(){
        screen = new HiRes16Color(Castpixel16.palette(), TIC80.font());
        background = new Bg();
        dog = new Dog();
        dog.run(); // "run" is one of the animations in the spritesheet
        
        titleInit();
    }
    
    // Might help in certain situations
    void shutdown(){
        screen = null;
    }
    
    // update is called by femto.Game every frame
    void update(){
        screen.clear(0);
        switch(gardenState){
            case TITLE:
                titleUpdate();
                break;
            case MAINMENU:
                screen.setTextColor(15);
                screen.setTextPosition(5, 5);
                screen.textWidth(220);
                screen.println("Welcome to my garden! As you can see, it has become quite decrepit. You see, I don't have a lot of time to tend to the garden anymore.");
                screen.println("If you're up to the task, I can pay you for your work!");
                
                break;
        }
        
        // Update the screen with everything that was drawn
        screen.flush();
    }
    
}
