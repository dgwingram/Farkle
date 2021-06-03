/** Program Description
 *  Author: dgwingram
 *  Description:
 *      Making the dice game known as Frackle or 10,000 in a mobile format that can be transmitted between devices
 *      or played on one device.  Will keep score and allow players to choose between typical rules.
 * */
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {


    //Point Calculation variables
    var newTurn:Boolean = false
    var diceSelected:Array<Boolean> = arrayOf(false,false,false,false,false,false)
    var diceSetAside = arrayOf(0,0,0,0,0,0)
    lateinit  var diceList : Array<Dice>


    // Farkle Rule Variables
    var sixOneRules = true //Six 1s = 5,000 points
    var pocketFarkle = false //three 1s = 300 points
    var hotDice = true //if all six dice have a score, must re-roll at least once to bank points
    var toxicTwo = false // lose turn and set aside points that haven't been banked if 4 or more 2s rolled
    var greed = true // can use the left over dice from previous player if they rolled hot dice
    var doubleTriple = false // role 6 dice of the same value = 2,500 points
    var welfare = false // must roll exactly 10,000 points. if over, set aside points go to player with the lowest score
    var openingScoreMinPoints = 300 // must role minimum 0|300|500|1,000 to be able to bank for future turns
    var openingScoreAcheived = false // Used to not require minimum score after being acheived



    // Dice button variables
    var diceStateArray = arrayOf(DiceState.DEFAULT,DiceState.DEFAULT,DiceState.DEFAULT,DiceState.DEFAULT,DiceState.DEFAULT,DiceState.DEFAULT)
    val diceSelectedColor = Color.YELLOW
    val diceBackgroundColor = Color.WHITE
    val diceTextColor = Color.parseColor("#484549")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       initGame()


    }

    private fun initGame(){
        //initialize Dice Array
        diceList = arrayOf<Dice>(
            Dice(R.id.btnDie1,btnDie1),
            Dice(R.id.btnDie2,btnDie2),
            Dice(R.id.btnDie3,btnDie3),
            Dice(R.id.btnDie4,btnDie4),
            Dice(R.id.btnDie5,btnDie5),
            Dice(R.id.btnDie6,btnDie6))

        txtConsole.text = "\u2680 \u2681 \u2682 \u2683 \u2684 \u2685 "
        txtConsole.text =
            txtConsole.text.toString() + "\u2680 \u2681 \u2682 \u2683 \u2684 \u2685 "
    }

    fun roll(view: View){
        for(die in diceList){
            txtConsole.text =txtConsole.text.toString() +  die.rollDice()
        }
    }

    fun diceOnclick(view: View) {
        for(die in diceList){
            if(view.id == die.id)
                die.selectDie()
        }
        
    }

    fun rollDiceClicked(view:View){
        var selectedDiceCount = 0

        for(die in diceSelected){
            if(die) selectedDiceCount ++
        }
        for((index, die) in diceList.withIndex()){
            if(selectedDiceCount >= 6) {
                die.reset()
                diceSelected[index] = false
                diceSetAside[index] = 0
            }
            else if(die.state == DiceState.DEFAULT) {
                val diceNum = die.rollDice()
                txtConsole.text = txtConsole.text.toString() + " $diceNum"
            }
            else if (die.state == DiceState.SELECTED) {

                diceSetAside[index] = die.faceValue
                diceSelected[index] = true
                die.bankDie()
            }
        }
    }
    fun BankDiceClicked(view:View){
        calculatePoints()
        for(die in diceList){

            die.reset()
        }
    }


    @SuppressLint("ResourceAsColor")
    fun changeDiceState(dice:Button, position:Int){
        Log.i("Dice Pressed","ChangeDiceState with ${dice.toString()} at $position")
        when(dice.text.toString().toInt()){
            1-> diceSetAside[0]+=1
            2-> diceSetAside[1]+=1
            3-> diceSetAside[2]+=1
            4-> diceSetAside[3]+=1
            5-> diceSetAside[4]+=1
            6-> diceSetAside[5]+=1
        }
        when(diceStateArray[position]){
            DiceState.DEFAULT ->{
                diceStateArray[position]=DiceState.SELECTED
                dice.setBackgroundColor(diceSelectedColor)
            }
            DiceState.SELECTED->{
                diceStateArray[position]=DiceState.DEFAULT
                dice.setBackgroundColor(diceBackgroundColor)

            }
        }

    }
    fun calculatePoints(){
        var rolePoints = 0
        var diceCount = 0

        var count1 = 0
        var count2 = 0
        var count3 = 0
        var count4 = 0
        var count5 = 0
        var count6 = 0

        for (die in diceSetAside){
            when(die){
                1 -> count1 ++
                2 -> count2 ++
                3 -> count3 ++
                4 -> count4 ++
                5 -> count5 ++
                6 -> count6 ++
            }
        }

        if(count6 == 6)

        for(i in diceSetAside.indices){
            var pointBase = diceSetAside[i]*100


            if(i==0){
                var pointBase = 1000
                if(pocketFarkle) pointBase = 300

                if(diceSetAside[0]==6){
                    if(sixOneRules) rolePoints+=5000
                    else rolePoints += 1000
                }else if(diceSetAside[0]==5){
                    rolePoints = pointBase *3
                }else if(diceSetAside[0]==4){

                }else if(diceSetAside[0]==3){

                }else{

                }


            }
            else if (i==4){

            }else{

            }


        }
    }
}
