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
    MINIGAME,
    PAUSEMENU,
    GAMEOVER
}

class Main extends State {
    
    //Default start at TITLE screen state
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
                    //Clear the save data here
                }
                break;
        }
        dog.draw(screen);
    }
    //END TITLE UPDATE

    //START RUNNING UPDATE
    int cx, cy, insects, time;
    void runningInit(){
        cx = 10;
        cy = 10;
        insects = 0;
        time = 0;
        bug = new Bug();
        wings = new Wing();
        wings.flap();
    }
    void runningUpdate(){
        time++;
        if(time >= 100) time = 0;
        
        if(insects == 0 && time > 98){
            insects = Math.random(1, 9);
            //IF INSECT NOT ON PLANT, RESET INSECTS
            System.out.println("Set insects: " + insects);
        }
        int tile = 1;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int x = 40*i+40;
                int y = 40*j+40;
                screen.setTextPosition(x, y);
                screen.print(": "+tile);
                if(cx+8 > x && cx+8 < x + 32 && cy+8 > y && cy+8 < y+32){
                    screen.drawRect(x, y, 32, 32, 7);
                    if(insects == tile && Button.B.justPressed()){
                        minigameInit(5);
                        gardenState = GardenState.MINIGAME;
                        return;
                    }
                }else{
                    screen.drawRect(x, y, 32, 32, 2);
                }
                if(insects == tile){
                    bug.draw(screen, x, y);
                    wings.draw(screen, x+4, y-8);
                }
                tile++;
            }
        }
        
        if(Button.Left.isPressed()){
            cx--;
            dog.setMirrored(true);
        }
        if(Button.Right.isPressed()){
            cx++;
            dog.setMirrored(false);
        }
        if(Button.Down.isPressed()){
            cy++;
        }
        if(Button.Up.isPressed()){
            cy--;
        }
        dog.draw(screen, cx, cy);
    }
    //END RUNNING UPDATE
    
    //START MINIGAME UPDATE 
    Leaf leaf;
    Bug bug;
    int bgx, bgy;
    Wing wings;
    SwatterUp swatUp;
    
    int swatReload, swx, swy, numBugs;
    void minigameInit(int num){
        numBugs = num;
        leaf = new Leaf();
        bug = new Bug();
        bgx = 40;
        bgy = 40;
        wings = new Wing();
        wings.flap();
        
        swatUp = new SwatterUp();
        swx = 50;
        swy = 50;
        swatReload = 25;
    }
    
    void minigameUpdate(){
        if(numBugs == 0){
            screen.drawRect(30, 30, 130, 45, 7);
            screen.setTextPosition(32, 32);
            screen.setTextColor(7);
            screen.println("The plant is safe!");
            if(Button.B.justPressed()){
                insects = 0;
                gardenState = GardenState.RUNNING;
            }
            return;   
        }
        //draw stem
        screen.fillRect(100, 0, 20, 180, 7);
        screen.drawRect(99, 0, 21, 180, 15);
        leaf.draw(screen, 70, 50);
        leaf.draw(screen, 118, 80, true, false, false);
        
        if(bgx > 110) bgx--;
        if(bgx < 110) bgx++;
        if(bgy > 80) bgy --;
        if(bgy < 80) bgy++;
        
        if(bgx > 110)
            bug.draw(screen, bgx, bgy, true, false, false);
        else
            bug.draw(screen, bgx, bgy, false, false, false);
            
        wings.draw(screen, bgx+4, bgy-8);
        
        if(swatReload > 0){
            swatReload--;
            swatUp.draw(screen, swx, swy, false, true, false);
        }else{
            swatUp.draw(screen, swx, swy, false, false, false);
        }
        
        if(Button.Left.isPressed()){
            swx--;
        }
        if(Button.Right.isPressed()){
            swx++;
        }
        if(Button.Up.isPressed()){
            swy--;
        }
        if(Button.Down.isPressed()){
            swy++;
        }
        
        if(swatReload == 0 && Button.B.justPressed()){
            swatReload = 25;
            if(swx+8 > bgx && swx+8 < bgx+bug.width() && swy+4 > bgy && swy+4 < bgy + bug.height()){
                bgx = Math.random(-2, 2) > 0 ? 240 : -80;
                bgy = Math.random(-40, 200);
                numBugs--;
            }
        }
        
        
    }
    
    
    //END MINIGAME UPDATE 

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
                screen.setTextPosition(0, 0);
                screen.textRightLimit = 210;
                screen.println("Welcome to my garden! As you can see, it has become quite decrepit.\nYou see, I don't have a lot of time to tend to the garden anymore.");
                screen.println("If you're up to the task, I can pay you for your work!");
                if(Button.A.justPressed()){
                    //init running state
                    runningInit();
                    gardenState = GardenState.RUNNING;
                }
                break;
            case RUNNING:
                screen.setTextColor(15);
                screen.setTextPosition(0, 0);
                screen.textRightLimit = 210;
                screen.println("Running screen");
                runningUpdate();
                //tmp
                if(Button.B.justPressed()){
                    minigameInit(Math.random(5, 15));
                    gardenState = GardenState.MINIGAME;
                    
                }
                break;
            case MINIGAME:
                minigameUpdate();
                break;
            case PAUSEMENU:
                break;
            case GAMEOVER:
                break;
            
        }
        
        // Update the screen with everything that was drawn
        screen.flush();
    }
    
}
