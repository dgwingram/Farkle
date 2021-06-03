import android.graphics.Color
import android.view.View
import android.widget.Button

open class Dice(pID:Int, button: Button){

    open var id:Int = pID
    open lateinit var state:DiceState
    private var die:Button = button
    open var faceValue:Int = (0..7).random()

    private val diceSelectedColor = Color.YELLOW
    private val diceBackgroundColor = Color.WHITE

    init{
        reset()
    }

    fun reset():Unit{
        state = DiceState.DEFAULT
        die.isEnabled = true
        die.setBackgroundColor(diceBackgroundColor)
    }
    fun rollDice():String{
        var rolledNum = (0..7).random()
        die.text = rolledNum.toString()
        return rolledNum.toString()
    }
    fun selectDie(){
        if(state != DiceState.BANKED)
        if (state == DiceState.SELECTED){
            state = DiceState.SELECTED
            die.setBackgroundColor(diceSelectedColor)
        } else {
            state = DiceState.DEFAULT
            die.setBackgroundColor(diceBackgroundColor)
        }

    }
    fun bankDie():Int{
        if(state == DiceState.SELECTED) {
            state = DiceState.BANKED
            die.isEnabled = false
            return faceValue
        }
        else return 0
    }
}
