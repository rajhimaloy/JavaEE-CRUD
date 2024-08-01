/**
 * 
 */
package com.banks.web.sa.auth.webapi;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.banks.erp.library.util.crypto.KeyGenerator;
import com.banks.erp.library.util.crypto.PasswordUtil;
import com.banks.erp.sa.webapi.model.WebApiUser;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class WebApiUserEndpoint {

	// ======================================
    // =          Injection Points          =
    // ======================================

    @Context
    private UriInfo uriInfo;

    @Inject
    private Logger logger;

    @Inject
    private KeyGenerator keyGenerator;

    @PersistenceContext
    private EntityManager em;

    // ======================================
    // =          Business methods          =
    // ======================================

    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("login") String login,
                                     @FormParam("password") String password) {

        try {

            logger.info("#### login/password : " + login + "/" + password);

            // Authenticate the user using the credentials provided
            authenticate(login, password);

            // Issue a token for the user
            String token = issueToken(login);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String login, String password) throws Exception {
        TypedQuery<WebApiUser> query = em.createNamedQuery(WebApiUser.FIND_BY_LOGIN_PASSWORD, WebApiUser.class);
        query.setParameter("login", login);
        query.setParameter("password", PasswordUtil.digestPassword(password));
        WebApiUser webApiUser = query.getSingleResult();

        if (webApiUser == null)
            throw new SecurityException("Invalid webApiUser/password");
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }

    @POST
    @Path("/usersetup")
    public Response create(WebApiUser webApiUser) {
        em.persist(webApiUser);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(webApiUser.getId()).build()).build();
    }

    //http://localhost:8080/saleswebapi/users/7c0481a08abe0ca9d96757d281fffc3021c53att
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") String id) {
        WebApiUser webApiUser = em.find(WebApiUser.class, id);

        if (webApiUser == null)
            return Response.status(NOT_FOUND).build();

        return Response.ok(webApiUser).build();
    }

    //http://localhost:8080/saleswebapi/users
    @GET
    public Response findAllUsers() {
        TypedQuery<WebApiUser> query = em.createNamedQuery(WebApiUser.FIND_ALL, WebApiUser.class);
        List<WebApiUser> allUsers = query.getResultList();

        if (allUsers == null)
            return Response.status(NOT_FOUND).build();

        return Response.ok(allUsers).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") String id) {
        em.remove(em.getReference(WebApiUser.class, id));
        return Response.noContent().build();
    }

    // ======================================
    // =          Private methods           =
    // ======================================

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
