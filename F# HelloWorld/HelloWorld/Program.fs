// Learn more about F# at http://fsharp.org
// See the 'F# Tutorial' project for more help.

//[<EntryPoint>]
//let main argv = 
//    printfn "%A" argv
    //0 // return an integer exit code

#light
//#indent "off"

printf "Hello World \n"

// Doubles a number
let multiply(x) = x * 2
let multi1 = multiply(5)
let multi2 = multiply(44)

// Add one to a number
let addOne(x) = x + 1
let addOne1 = addOne(5)
let addOne2 = addOne(0)

// Returns true if a number is even and false, if not
//let isEven(x) = x%2 = 0
let isEven(x) = if x%2 = 0 then true else false
let even1 = isEven(5)
let even2 = isEven(2)

// Returns the maximum of two numbers
let highestNumber(x,y) = if x<y then y else x
let highnumber1 = highestNumber(5,8)
let highnumber2 = highestNumber(2,2)

// Product of elements in list
let rec listProduct(x) =
    match x with
    | head :: rest ->
        x.Head * listProduct(x.Tail)
    | [] -> 1

let listProduct1 = listProduct([3; 2; 4])

// Count of elements greater than value.
let rec countElementsGreaterThan(list, value) =
    match list with
    | head :: rest ->
        (if list.Head > value then 1 else 0) + countElementsGreaterThan(list.Tail, value)
    | [] -> 0

let elementCount = countElementsGreaterThan([3;4;5;6;7;8;9;10;11;12], 5)

// Cocate a list with lists. Like putting multiple lists within a list together as one list.
let rec concatList list =
    match list with
    | head :: tail -> head @ (concatList tail)
    | [] -> []

let concatList1 = concatList([[2;3];[1;2;3]])

// Halve all elements greater than value.

let splitGreaterThan x = 
    if x > 20 then x / 2 else x
    
let splitGreaterThan1 = List.map splitGreaterThan [5; 10; 20; 24; 30; 34; 40]
