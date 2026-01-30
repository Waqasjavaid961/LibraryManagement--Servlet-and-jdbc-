<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.*, Enums.*, java.util.*, DAO.*" %>

<%
    // Session aur Role Check
    Users user = (Users) session.getAttribute("user");
    if(user == null){
        response.sendRedirect("index.jsp");
        return;
    }
    String role = user.getRole().name().toLowerCase();
    
    // Services Initialize
    BookAddService bookAddService = new BookAddService();
    BookBuyingService bookBuyingService = new BookBuyingService();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System | Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f0f2f5; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .sidebar { background: white; border-radius: 15px; height: fit-content; position: sticky; top: 20px; }
        .nav-pills .nav-link { color: #495057; font-weight: 500; transition: 0.3s; margin-bottom: 5px; }
        .nav-pills .nav-link.active { background-color: #0d6efd; color: white; box-shadow: 0 4px 10px rgba(13, 110, 253, 0.3); }
        .card { border: none; border-radius: 15px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        .badge-qty { width: 45px; display: inline-block; }
    </style>
</head>
<body>

<div class="container py-4">
    <div class="card p-3 mb-4 d-flex flex-row justify-content-between align-items-center">
        <h2 class="m-0 text-primary fw-bold">📚 Library Portal</h2>
        <div class="text-end">
            <span class="text-muted d-block">Welcome, <strong><%= user.getName() %></strong></span>
            <span class="badge bg-info text-dark text-uppercase"><%= role %></span>
            <a href="LogoutServlet" class="btn btn-outline-danger btn-sm ms-3">Logout</a>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-3">
            <div class="sidebar p-3 shadow-sm mb-4">
                <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist">
                    <button class="nav-link active" data-bs-toggle="pill" data-bs-target="#profile">👤 Profile</button>
                    <button class="nav-link" data-bs-toggle="pill" data-bs-target="#all-books">📖 View Library</button>

                    <%-- USER ONLY --%>
                    <% if("user".equals(role)) { %>
                        <button class="nav-link" data-bs-toggle="pill" data-bs-target="#user-borrow">🛒 Borrow Book</button>
                        <button class="nav-link" data-bs-toggle="pill" data-bs-target="#user-history">📜 My Borrowed Books</button>
                    <% } %>

                    <%-- ADMIN & SUPERADMIN --%>
                    <% if("admin".equals(role) || "superadmin".equals(role)) { %>
                        <hr>
                        <button class="nav-link" data-bs-toggle="pill" data-bs-target="#admin-add">➕ Add New Book</button>
                        <button class="nav-link" data-bs-toggle="pill" data-bs-target="#admin-ops">⚙️ Manage Books</button>
                    <% } %>

                    <%-- SUPERADMIN ONLY --%>
                    <% if("superadmin".equals(role)) { %>
                        <hr>
                        <button class="nav-link" data-bs-toggle="pill" data-bs-target="#super-mgmt">🛡️ Admin Controls</button>
                    <% } %>
                    
                    <button class="nav-link mt-3" data-bs-toggle="pill" data-bs-target="#settings">⚙️ Settings</button>
                </div>
            </div>
        </div>

        <div class="col-lg-9">
            <div class="tab-content" id="v-pills-tabContent">
                
                <div class="tab-pane fade show active" id="profile">
                    <div class="card p-5 text-center">
                        <img src="https://ui-avatars.com/api/?name=<%= user.getName() %>&size=120&background=0d6efd&color=fff" class="rounded-circle mx-auto mb-3">
                        <h3><%= user.getName() %></h3>
                        <p class="text-muted"><%= user.getGmail() %></p>
                        <p>Total Books Borrowed: <span class="badge bg-dark"><%= bookBuyingService.show(user.getId()).size() %></span></p>
                    </div>
                </div>

                <div class="tab-pane fade" id="all-books">
                    <div class="card p-4">
                        <h4 class="mb-3">Books in Library</h4>
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead class="table-light">
                                    <tr><th>ID</th><th>Title</th><th>Author</th><th>Category</th><th>Stock</th><th>Price</th></tr>
                                </thead>
                                <tbody>
                                    <% List<Books> list = bookAddService.booksList();
                                       for(Books b : list) { %>
                                       <tr>
                                           <td><strong>#<%= b.getId() %></strong></td>
                                           <td><%= b.getBookname() %></td>
                                           <td><%= b.getAuthor_name() %></td>
                                           <td><span class="badge bg-secondary"><%= b.getCatagory() %></span></td>
                                           <td><span class="badge badge-qty <%= b.getQuantity() > 0 ? "bg-success" : "bg-danger" %>"><%= b.getQuantity() %></span></td>
                                           <td>$<%= b.getPrice() %></td>
                                       </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <% if("user".equals(role)) { %>
                <div class="tab-pane fade" id="user-borrow">
                    <div class="card p-4">
                        <h4 class="text-success mb-3">Borrow a Book</h4>
                        <form action="BorrowBookInput" method="POST">
                            <div class="row g-3">
                                <div class="col-md-6"><input type="text" name="catagory" class="form-control" placeholder="Category Name" required></div>
                                <div class="col-md-6"><input type="text" name="bookname" class="form-control" placeholder="Exact Book Name" required></div>
                                <div class="col-md-4"><input type="number" name="bookId" class="form-control" placeholder="Book ID" required></div>
                                <div class="col-md-4"><input type="number" name="quantity" class="form-control" placeholder="Quantity" min="1" required></div>
                                <div class="col-md-4"><input type="number" name="price" class="form-control" placeholder="Unit Price" required></div>
                                <input type="hidden" name="availableQty" value="100">
                                <button class="btn btn-success w-100">Confirm Borrow Request</button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="tab-pane fade" id="user-history">
                    <div class="card p-4">
                        <h4 class="mb-3 text-primary">Your Borrowed Books</h4>
                        <table class="table">
                            <thead><tr><th>ID</th><th>Book</th><th>Qty</th><th>Total Cost</th><th>Action</th></tr></thead>
                            <tbody>
                                <% List<CustomerDetails> myDetails = bookBuyingService.show(user.getId());
                                   for(CustomerDetails det : myDetails) { %>
                                   <tr>
                                       <td><%= det.getBook_id() %></td>
                                       <td><%= det.getBookname() %></td>
                                       <td><%= det.getQuantity() %></td>
                                       <td>$<%= det.getTotal() %></td>
                                       <td>
                                           <form action="ReturnBookInput" method="POST">
                                               <input type="hidden" name="bookId" value="<%= det.getBook_id() %>">
                                               <input type="hidden" name="quantity" value="<%= det.getQuantity() %>">
                                               <button class="btn btn-outline-primary btn-sm">Return Book</button>
                                           </form>
                                       </td>
                                   </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <% } %>

                <% if("admin".equals(role) || "superadmin".equals(role)) { %>
                <div class="tab-pane fade" id="admin-add">
                    <div class="card p-4">
                        <h4 class="text-primary mb-3">Add New Book to Stock</h4>
                        <form action="AddBookInput" method="POST">
                            <div class="row g-3">
                                <div class="col-md-6"><input type="text" name="catagory" class="form-control" placeholder="Category" required></div>
                                <div class="col-md-6"><input type="text" name="bookname" class="form-control" placeholder="Book Name" required></div>
                                <div class="col-md-6"><input type="number" name="isbn" class="form-control" placeholder="ISBN" required></div>
                                <div class="col-md-6"><input type="text" name="authorname" class="form-control" placeholder="Author" required></div>
                                <div class="col-md-4"><input type="text" name="edition" class="form-control" placeholder="Edition" required></div>
                                <div class="col-md-4"><input type="text" name="language" class="form-control" placeholder="Language" required></div>
                                <div class="col-md-4"><input type="number" name="quantity" class="form-control" placeholder="Qty" required></div>
                                <div class="col-12"><input type="number" name="price" class="form-control" placeholder="Price" required></div>
                                <button class="btn btn-primary">Save Book</button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="tab-pane fade" id="admin-ops">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="card p-4">
                                <h6>Search for Update</h6>
                                <form action="UpdateBookServlet" method="GET">
                                    <input type="number" name="id" class="form-control mb-2" placeholder="Book ID">
                                    <button class="btn btn-info w-100 text-white">Fetch</button>
                                </form>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card p-4 text-center">
                                <h6>Remove Book</h6>
                                <form action="RemoveBookInput" method="POST">
                                    <input type="number" name="id" class="form-control mb-2" placeholder="Book ID">
                                    <button class="btn btn-danger w-100">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>

                <% if("superadmin".equals(role)) { %>
                <div class="tab-pane fade" id="super-mgmt">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="card p-4 border-primary">
                                <h6>Promote User to Admin</h6>
                                <form action="AddInput" method="POST">
                                    <input type="number" name="id" class="form-control mb-2" placeholder="User ID">
                                    <button class="btn btn-primary w-100">Promote</button>
                                </form>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card p-4 border-warning">
                                <h6>Deactivate Admin</h6>
                                <form action="DeactiveInput" method="POST">
                                    <input type="number" name="id" class="form-control mb-2" placeholder="Admin ID">
                                    <button class="btn btn-warning w-100">Deactivate</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>

            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>