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

 




