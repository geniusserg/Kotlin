class Complex(val real: Float, val imag: Float) {
    constructor(): this(0f, 0f)
    constructor(num: Float): this(num, 0f)
    operator fun plus(other: Complex): Complex {
        return Complex(real + other.real, imag + other.imag)
    }
    operator fun minus(other: Complex): Complex{
        return Complex(real - other.real, imag - other.imag)
    }
    operator fun times(other: Complex): Complex{
        return Complex((real * other.real) + (imag*other.imag*(-1)), (imag*other.real) + (real * other.imag))
    }
    override fun toString(): String {
        var sign = when(imag < 0){
            true -> "-"
            false -> "+"
        }
        return " $real$sign${Math.abs(imag)}*i "
    }
}


class Matrix {

    private var matrix: Array<Array<Complex?>>
    private var rowsNum: Int
    private var colsNum: Int

    constructor(n: Int, m: Int)
    {
        this.rowsNum = m
        this.colsNum = n
        matrix = Array(n) { arrayOfNulls(m) }
        for (i in 0..n-1)
        {
            for (j in 0..m-1)
            {
                matrix[i][j] = Complex()
            }
        }
    }

    constructor(matrix: Array<Array<Complex>>)
    {
        rowsNum = matrix.size
        colsNum = matrix[0].size
        this.matrix = Array(rowsNum) { arrayOfNulls(colsNum) }
        for (i in 0..colsNum-1)
        {
            for (j in 0..rowsNum-1)
            {
                this.matrix[i][j] =
                    Complex(matrix[i][j].real, matrix[i][j].imag)
            }
        }
    }
    
    constructor(matrix: Array<Array<Float>>)
    {
        rowsNum = matrix.size
        colsNum = matrix[0].size
        this.matrix = Array(rowsNum) { arrayOfNulls(colsNum) }
        for (i in 0..colsNum-1)
        {
            for (j in 0..rowsNum-1)
            {
                this.matrix[i][j] = Complex(matrix[i][j], 0f)
            }
        }
    }

    fun transpose(): Matrix
    {
        val x = Matrix(colsNum, rowsNum)
        for (i in 0..colsNum-1)
        {
            for (j in 0..rowsNum-1)
            {
                x.setValue(matrix[i][j], j, i)
            }
        }
        return x
    }

    operator fun plus(x: Matrix): Matrix
    {
        val tmp = Matrix(colsNum, rowsNum)
        for (i in 0..colsNum-1)
        {
            for (j in 0..rowsNum-1)
            {
                tmp.setValue(matrix[i][j]!! + x.matrix[i][j]!!, i, j)
            }
        }
        return tmp
    }

    operator fun minus(x: Matrix): Matrix
    {
        val tmp = Matrix(colsNum, rowsNum)
        for (i in 0..colsNum-1)
        {
            for (j in 0..rowsNum-1)
            {
                tmp.setValue(matrix[i][j]!! - x.matrix[i][j]!!, i, j)
            }
        }
        return tmp
    }


    operator fun times(other: Matrix): Matrix
    {
        val tmp = Matrix(colsNum, other.rowsNum)
        for (i in 0..colsNum-1)
        {
            for (j in 0..other.rowsNum-1)
            {
                var x = Complex()
                for (k in 0..other.colsNum-1)
                {
                    x = x.plus(this.matrix[i][k]!! * other.getValue(k, j)!!)
                }
                tmp.setValue(x, i, j)
            }
        }
        return tmp
    }

    fun getRowsNum(): Int
    {
        return this.rowsNum
    }

    fun getColsNum(): Int
    {
        return this.colsNum
    }

    fun getValue(i: Int, j: Int): Complex?
    {
        return matrix[i][j]
    }

    fun setValue(x: Complex?, i: Int, j: Int)
    {
        matrix[i][j] = x!!
    }

    fun setValue(x: Float?, i: Int, j: Int)
    {
        matrix[i][j] = Complex(x!!)
    }

    override fun toString(): String
    {
        val str = StringBuilder()
        for (i in 0..colsNum-1)
        {
            for (j in 0..rowsNum-1) str.append(matrix[i][j]).append("  ")
            str.append("\n")
        }
        return str.toString()
    }
}

fun main(){
    var matrix:Array<Array<Float>> = Array<Array<Float>>(3) {i -> Array<Float>(3) { j -> 12f}}
    var aclass = Matrix(matrix)
    var bclass = Matrix(matrix)
    var result = aclass + bclass
    print(result)
    /* 	 24.0+0.0*i    24.0+0.0*i    24.0+0.0*i   
         24.0+0.0*i    24.0+0.0*i    24.0+0.0*i   
         24.0+0.0*i    24.0+0.0*i    24.0+0.0*i 
 */
 
 	var matrix2 = Array<Array<Complex>>(3) {i -> Array<Complex>(3) { j -> Complex(3f, -5f)}}
    var aclass2 = Matrix(matrix2)
    var bclass2 = Matrix(matrix2)
    var result2 = aclass2 * bclass2
    print(result2)
    /*
     -48.0-90.0*i    -48.0-90.0*i    -48.0-90.0*i   
 -48.0-90.0*i    -48.0-90.0*i    -48.0-90.0*i   
 -48.0-90.0*i    -48.0-90.0*i    -48.0-90.0*i  
     */ 
}
