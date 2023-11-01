/**
 * This package contains Data Access Objects (DAO) classes for the scheduling system.
 * <p>
 * The classes in this package provide methods to perform CRUD operations (Create, Read, Update, Delete)
 * on entities such as User, Contact, and Appointment. These operations are designed to interact with a
 * database using JDBC. The JDBC class was largely created by Malcolm Wabara (course instructor), although
 * I made some alterations.
 * </p>
 *
 * Each DAO class corresponds to an entity, providing operations specific to that entity.
 * Errors during operations are communicated through alerts
 * using the {@link lanej.schedulingsystem.helper.ScreenUtility} class.
 * </p>
 *
 * @author Jonathan Lane
 * @version 1.0
 */
package lanej.schedulingsystem.dao;