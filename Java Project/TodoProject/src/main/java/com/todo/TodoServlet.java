package com.todo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        if (action != null && idParam != null) {
            try (Connection conn = DBConnection.getConnection()) {
                if (action.equals("done")) {
                    PreparedStatement ps = conn.prepareStatement(
                            "UPDATE todos SET status='done' WHERE id=?");
                    ps.setInt(1, Integer.parseInt(idParam));
                    ps.executeUpdate();
                } else if (action.equals("delete")) {
                    PreparedStatement ps = conn.prepareStatement(
                            "DELETE FROM todos WHERE id=?");
                    ps.setInt(1, Integer.parseInt(idParam));
                    ps.executeUpdate();
                }
            } catch (SQLException e) { e.printStackTrace(); }
            res.sendRedirect("todo");
            return;
        }

        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>My Todo List</title>");
        out.println("<style>");
        out.println("* { margin:0; padding:0; box-sizing:border-box; }");
        out.println("body { font-family:'Segoe UI',sans-serif; background:linear-gradient(135deg,#667eea,#764ba2); min-height:100vh; padding:40px 20px; }");
        out.println(".container { max-width:650px; margin:0 auto; }");
        out.println("h2 { color:white; font-size:32px; margin-bottom:6px; text-align:center; }");
        out.println(".subtitle { color:rgba(255,255,255,0.8); text-align:center; margin-bottom:30px; font-size:15px; }");
        out.println(".form-card { background:white; border-radius:16px; padding:24px; margin-bottom:20px; box-shadow:0 8px 32px rgba(0,0,0,0.15); }");
        out.println(".form-row { display:flex; gap:10px; }");
        out.println("input[type=text] { flex:1; padding:12px 16px; border:2px solid #e0e0e0; border-radius:10px; font-size:15px; outline:none; transition:border 0.2s; }");
        out.println("input[type=text]:focus { border-color:#667eea; }");
        out.println("button { padding:12px 24px; background:linear-gradient(135deg,#667eea,#764ba2); color:white; border:none; border-radius:10px; font-size:15px; font-weight:bold; cursor:pointer; transition:transform 0.2s; }");
        out.println("button:hover { transform:translateY(-1px); }");
        out.println(".todo-card { background:white; border-radius:16px; overflow:hidden; box-shadow:0 8px 32px rgba(0,0,0,0.15); }");
        out.println(".todo-header { display:grid; grid-template-columns:50px 1fr 120px 160px; background:linear-gradient(135deg,#667eea,#764ba2); color:white; padding:14px 20px; font-weight:bold; font-size:14px; }");
        out.println(".todo-row { display:grid; grid-template-columns:50px 1fr 120px 160px; padding:14px 20px; border-bottom:1px solid #f0f0f0; align-items:center; transition:background 0.2s; }");
        out.println(".todo-row:hover { background:#f8f9ff; }");
        out.println(".todo-row:last-child { border-bottom:none; }");
        out.println(".badge-pending { background:#fff3e0; color:#f57c00; padding:4px 12px; border-radius:20px; font-size:13px; font-weight:bold; display:inline-block; }");
        out.println(".badge-done { background:#e8f5e9; color:#388e3c; padding:4px 12px; border-radius:20px; font-size:13px; font-weight:bold; display:inline-block; }");
        out.println(".btn-done { background:#e3f2fd; color:#1976d2; padding:6px 12px; border-radius:8px; text-decoration:none; font-size:13px; font-weight:bold; margin-right:6px; transition:background 0.2s; }");
        out.println(".btn-done:hover { background:#bbdefb; }");
        out.println(".btn-del { background:#ffebee; color:#c62828; padding:6px 12px; border-radius:8px; text-decoration:none; font-size:13px; font-weight:bold; transition:background 0.2s; }");
        out.println(".btn-del:hover { background:#ffcdd2; }");
        out.println(".empty { text-align:center; padding:40px; color:#aaa; font-size:16px; }");
        out.println(".stats { display:flex; gap:12px; margin-bottom:20px; }");
        out.println(".stat { flex:1; background:rgba(255,255,255,0.2); border-radius:12px; padding:16px; text-align:center; color:white; }");
        out.println(".stat-num { font-size:28px; font-weight:bold; }");
        out.println(".stat-label { font-size:13px; opacity:0.9; }");
        out.println("</style></head><body>");
        out.println("<div class='container'>");
        out.println("<h2>&#128221; My Todo List</h2>");
        out.println("<p class='subtitle'>Stay organized. Get things done.</p>");

        // Stats
        int total = 0, done = 0, pending = 0;
        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM todos");
            if (rs.next()) total = rs.getInt(1);
            rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM todos WHERE status='done'");
            if (rs.next()) done = rs.getInt(1);
            pending = total - done;
        } catch (SQLException e) { e.printStackTrace(); }

        out.println("<div class='stats'>");
        out.println("<div class='stat'><div class='stat-num'>" + total + "</div><div class='stat-label'>Total Tasks</div></div>");
        out.println("<div class='stat'><div class='stat-num'>" + pending + "</div><div class='stat-label'>Pending</div></div>");
        out.println("<div class='stat'><div class='stat-num'>" + done + "</div><div class='stat-label'>Completed</div></div>");
        out.println("</div>");

        // Add form
        out.println("<div class='form-card'>");
        out.println("<form method='post' action='todo'>");
        out.println("<div class='form-row'>");
        out.println("<input type='text' name='task' placeholder='Add a new task...' required/>");
        out.println("<button type='submit'>+ Add Task</button>");
        out.println("</div></form></div>");

        // Todo list
        out.println("<div class='todo-card'>");
        out.println("<div class='todo-header'><div>#</div><div>Task</div><div>Status</div><div>Actions</div></div>");

        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM todos ORDER BY id DESC");
            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                int id = rs.getInt("id");
                String task = rs.getString("task");
                String status = rs.getString("status");
                String badgeClass = status.equals("done") ? "badge-done" : "badge-pending";
                String badgeText = status.equals("done") ? "&#10003; DONE" : "&#9679; PENDING";

                out.println("<div class='todo-row'>");
                out.println("<div style='color:#aaa;font-weight:bold'>" + id + "</div>");
                out.println("<div style='font-size:15px'>" + task + "</div>");
                out.println("<div><span class='" + badgeClass + "'>" + badgeText + "</span></div>");
                out.println("<div>");
                if (status.equals("pending")) {
                    out.println("<a class='btn-done' href='todo?action=done&id=" + id + "'>&#10003; Done</a>");
                }
                out.println("<a class='btn-del' href='todo?action=delete&id=" + id +
                        "' onclick=\"return confirm('Delete this task?')\">&#128465; Delete</a>");
                out.println("</div></div>");
            }
            if (!hasRows) {
                out.println("<div class='empty'>No tasks yet! Add your first task above.</div>");
            }
        } catch (SQLException e) {
            out.println("<div style='padding:20px;color:red'>DB Error: " + e.getMessage() + "</div>");
        }

        out.println("</div></div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String task = req.getParameter("task");
        if (task != null && !task.trim().isEmpty()) {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO todos (task) VALUES (?)");
                ps.setString(1, task.trim());
                ps.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        res.sendRedirect("todo");
    }
}