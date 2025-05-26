 /* Test 1 (Basic Aritmetics) ----- EXPECTED OUTPUT: 1 */

 let a = 1;
 let b = 2;
 let c = 3;
 let d = 4;

 let e = a + b;
 let f = d - e;
 ((e * f)/c);;

 /* Test 2 (Identity Function) ----- EXPECTED OUTPUT: 96861 */

 let id = fn x => { x };
 id(96861);;

 /* Test 3 (Function with multiple arguments) ----- EXPECTED OUTPUT: 2025 */

 let add = fn x, y => { x + y };
 let s = add(10, 5);
 let c = add(10, s);
 let p = add(1000, c);
 add(p, 1000);;

 /* Test 4 (Higher Order Function) ----- EXPECTED OUTPUT 17 */
 let makeAdder = fn x => { fn y => { x + y } };
 let add10 = makeAdder(10);
 add10(7);;

 
 /* Test 5 (Box, Assign and Deref) ----- EXPECTED OUTPUT: 15 */

 let b = box(10);
 b := !b + 5;
 !b;;

/* Test 6 (Lazy Lists) ----- EXPECT OUTPUT: first 20 multiples of 2 */
let double = fn l => {
    match l {
        nil -> nil
        |
        h::t -> (h*2)::(double (t))
    }
};
let mkll = fn n =>
{
    if (n==0) {
        nil
    } else {
        n:?( mkll(n-1))
    }
};
let ll20 = mkll (20);
double ( ll20 ) ;;

/* Test 7 (Strict Lists) ----- EXPECT OUTPUT: first 10 squares */

let square = fn l => {
    match l {
        nil -> nil
        |
        h::t -> (h*h)::(square (t))
    }
};
let mkll = fn n =>
{
    if (n==0) {
        nil
    } else {
        n::( mkll(n-1))
    }
};
let ll10 = mkll (10);
square ( ll10 ) ;;


 




