package jdbc.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import jdbc.util.UtilApp;

public class InventoryMain {

	public static void main(String[] args) throws IOException, SQLException {
		Scanner scanner = new Scanner(System.in);

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		connection = UtilApp.getJdbcConnection();

		while (true) {
			System.out.println("\t***INVENTORY MANAGEMENT SYSTEM***");
			System.out.println("1. Add Item");
			System.out.println("2. Update An Item");
			System.out.println("3. Remove an Item");
			System.out.println("4. List All Items");
			System.out.println("5. Exit");

			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.print("Enter Id: ");
				int id = scanner.nextInt();
				System.out.print("Enter Name of the Item:");
				String itemName = scanner.next();
				System.out.print("Assigned To: ");
				String assignedName = scanner.next();

				LocalDateTime dateTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedDate = dateTime.format(formatter);

				String sqlInsertQuery = "insert into inventory (id, itemName, assignedTo, assignedDateTime) values(?,?,?,?)";
				if (connection != null) {
					pstmt = connection.prepareStatement(sqlInsertQuery);
				}
				pstmt.setInt(1, id);
				pstmt.setString(2, itemName);
				pstmt.setString(3, assignedName);
				pstmt.setString(4, formattedDate);

				// Execute the Query
				pstmt.executeUpdate();
				System.out.println("Item Added Successfully");
				break;
			case 2:
				System.out.print("Enter Id to Update: ");
				int idToUpdate = scanner.nextInt();
				System.out.print("Enter New Name to Assign: ");
				String newName = scanner.next();

				String sqlUpdateQuery = "update inventory set assignedTo = ? where id = ?";
				pstmt = connection.prepareStatement(sqlUpdateQuery);
				pstmt.setString(1, newName);
				pstmt.setInt(2, idToUpdate);

				int rows = pstmt.executeUpdate();
				if (rows > 0) {
					System.out.println("Updated Successfully");
				} else {
					System.out.println("Item Not found!");
				}
				break;
			case 3:
				System.out.print("Enter Item ID to Remove: ");
				int removeId = scanner.nextInt();

				String sqlDeleteQuery = "Delete from inventory where id = ?";
				pstmt = connection.prepareStatement(sqlDeleteQuery);
				pstmt.setInt(1, removeId);
				int rows1 = pstmt.executeUpdate();
				if (rows1 > 0) {
					System.out.println("Deleted Successfully");
				} else {
					System.out.println("Item Not found");
				}
				break;
			case 4:
				System.out.println("Listing All Items");
				String selectQuery = "select * from inventory";
				pstmt = connection.prepareStatement(selectQuery);
				resultSet = pstmt.executeQuery();

				while (resultSet.next()) {
					int ItemId = resultSet.getInt("id");
					String name = resultSet.getString("itemName");
					String assignedString = resultSet.getString("assignedTo");
					String assignedDateTimeString = resultSet.getString("assignedDateTime");

					System.out.println("ID: " + ItemId + " Item Name: " + name + " Assigned To: " + assignedString
							+ " Assigned Date and Time: " + assignedDateTimeString);
					System.out.println();
				}
				break;
			case 5:
				System.out.println("Exited...");
				UtilApp.closeResouces(connection, pstmt, resultSet);
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice");
				break;
			}
		}

	}

}
