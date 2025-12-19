package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
    }

    @GetMapping("")
    public ShoppingCart getCart(Principal principal)
    {
        User user = getCurrentUser(principal);
        return shoppingCartDao.getByUserId(user.getId());
    }

    @PostMapping("products/{productId}")
    public ShoppingCart addProduct(@PathVariable int productId, Principal principal)
    {
        User user = getCurrentUser(principal);
        shoppingCartDao.addOrIncrement(user.getId(), productId);
        return getCart(principal);
    }

    @DeleteMapping("")
    public void clearCart(Principal principal)
    {
        User user = getCurrentUser(principal);
        shoppingCartDao.clear(user.getId());
    }

    private User getCurrentUser(Principal principal)
    {
        if (principal == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String username = principal.getName();
        User user = userDao.getByUserName(username);

        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return user;
    }
}
