package ordersystem;
import java.util.Scanner;
public class MUBSStudent {

	public static void main(String[] args) {
		

		        
		        Scanner scanner = new Scanner(System.in);

		        // MUBS SMALLGATE ORDER SYSTEM Menu Items and Prices (UGX)
		        String[] menuNames = { "Local Rolex", "Kikomando", "Matooke & Meat", "Fresh Passion Juice", "Samosa Pair" };
		        double[] menuPrices = { 3500.0, 2500.0, 7000.0, 2000.0, 1500.0 };

		        double walletBalance = 30000.0;
		        int correctPin = 2026;
		        int enteredPin;
		        int loginAttempts = 0;
		        boolean isAuthenticated = false;
		        boolean promoDiscountActive = false; // BUG FIX #2: promo now actually tracked

		        System.out.println("MUBS SMALLGATE ORDER SYSTEM");

		        // WHILE LOOP
		        // Authenticates user PIN with a 3-attempt limit.
		        while (loginAttempts < 3 && !isAuthenticated) {
		            System.out.print("Enter 4-Digit Student PIN: ");
		            enteredPin = readValidInt(scanner, "Invalid PIN format! Enter numbers only: ");
		            loginAttempts++;

		            //PIN check
		            isAuthenticated = (enteredPin == correctPin) ? true : false;

		            //Feedback message
		            String loginResult = isAuthenticated ? "Access Granted!" : "Incorrect PIN!";
		            System.out.println(loginResult);
		        }

		        if (!isAuthenticated) {
		            System.out.println("Terminal Locked: Exceeded 3 PIN attempts.");
		            scanner.close();
		            return;
		        }

		        int mainOption;

		        // DO-WHILE LOOP
		        do {

		            System.out.println(" MUBS SMALLGATE MAIN MENU");
		            System.out.println("1. Place an Order.");
		            System.out.println("2. Check Student Wallet & Top-Up Alert. ");
		            System.out.println("3. Apply BBC2 Student Promo Discount.");
		            System.out.println("4. Exit.");
		            System.out.print("Select an option (1-4): ");

		            mainOption = readValidInt(scanner, "Enter a valid choice (1-4): ");

		            switch (mainOption) {
		                case 1:
		                    System.out.println("--- CAFETERIA ITEM CATALOG ---");

		                    // FOR LOOP 
		                    for (int i = 0; i < menuNames.length; i++) {
		                        System.out.printf("[%d] %-18s : UGX %.2f\n", (i + 1), menuNames[i], menuPrices[i]);
		                    }

		                    System.out.print("How many distinct items do you want to order? ");
		                    int orderCount = readValidInt(scanner, "Please enter a whole number: ");

		                    // BUG FIX #4: guard against zero/negative order counts
		                    if (orderCount <= 0) {
		                        System.out.println("No items selected. Returning to main menu.");
		                        break;
		                    }

		                    double totalBill = 0.0;

		                    // FOR LOOP - Order Processing
		                    for (int step = 1; step <= orderCount; step++) {
		                        System.out.print("Select Item Code (1-5) for Selection #" + step + ": ");
		                        int itemCode = readValidInt(scanner, "Enter a number 1-5: ");

		                        if (itemCode >= 1 && itemCode <= 5) {
		                            int index = itemCode - 1;
		                            System.out.print("Enter quantity for " + menuNames[index] + ": ");
		                            int qty = readValidInt(scanner, "Enter a whole number: "); 

		                            double subtotal = menuPrices[index] * qty;
		                            totalBill += subtotal;
		                            System.out.printf(" -> Added %d x %s = UGX %.2f\n", qty, menuNames[index], subtotal);
		                        } else {
		                            System.out.println("Invalid item code selected! Skipped.");
		                        }
		                    }

		                    //  Promo discount 
		                    if (promoDiscountActive) {
		                        double discount = totalBill * 0.10;
		                        totalBill -= discount;
		                        System.out.printf("BBC2 Promo Applied: -UGX %.2f discount\n", discount);
		                        promoDiscountActive = false; 
		                    }

		                    System.out.printf("Total Calculated Order Cost: UGX %.2f\n", totalBill);

		                    // Funds validation
		                    String checkoutStatus = (totalBill <= walletBalance) ? "APPROVED" : "REJECTED";
		                    System.out.println("Checkout Status: " + checkoutStatus);

		                    if (checkoutStatus.equals("APPROVED")) {
		                        walletBalance -= totalBill;
		                        System.out.printf("Order Successful! Remaining Wallet Balance: UGX %.2f\n", walletBalance);
		                    } else {
		                        System.out.println("Order Failed! Insufficient funds in student wallet.");
		                    }
		                    break;

		                case 2:
		                    System.out.printf("\nCurrent Wallet Balance: UGX %.2f\n", walletBalance);

		                    // Balance status
		                    String balanceAlert = (walletBalance < 5000) ? "Low Balance! Top up soon." : "Sufficient Balance";
		                    System.out.println("Account Standing: " + balanceAlert);
		                    break;

		                case 3:
		                    System.out.print("Enter Student Promo Code: ");
		                    String promoCode = scanner.next();

		                    // Discount check
		                    boolean validPromo = promoCode.equalsIgnoreCase("BBC2");
		                    String promoResult = validPromo ? "10% Discount Applied to Next Order!" : "Invalid Promo Code";
		                    System.out.println("Promo Status: " + promoResult);

		                    if (validPromo) {
		                        promoDiscountActive = true; 
		                    }
		                    break;

		                case 4:
		                    System.out.println("Thank you for using MUBS Smallgate Order System. Enjoy your meal!");
		                    break;

		                default:
		                    System.out.println("Invalid choice! Select between 1 and 4.");
		            }

		        } while (mainOption != 4);

		        scanner.close();
		    }

		    
		    private static int readValidInt(Scanner scanner, String errorPrompt) {
		        while (!scanner.hasNextInt()) {
		            System.out.print(errorPrompt);
		            scanner.next();
		        }
		        return scanner.nextInt();
		    }
		

	}


