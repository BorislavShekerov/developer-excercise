package com.yostoya.shoptill.web;


import com.yostoya.shoptill.domain.HttpResponse;
import com.yostoya.shoptill.domain.dto.ItemDto;
import com.yostoya.shoptill.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/item/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<HttpResponse> addItem(@RequestBody @Valid final ItemDto newItem) {

        final ItemDto created = adminService.addItem(newItem);
        return ResponseEntity
                .created(getCreatedUri(created.getId()))
                .body(new HttpResponse(CREATED, "Item added", Map.of("item", created)));

    }

    @PutMapping("/item/{id}/edit")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<HttpResponse> update(@PathVariable final Long id,
                                               @RequestBody @Valid final ItemDto updateItem) {

        final ItemDto updated = adminService.update(id, updateItem);
        return ok(new HttpResponse(OK, "Item updated.", Map.of("item", updated)));

    }

    @DeleteMapping("/{id}/remove")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<HttpResponse> delete(@PathVariable final Long id) {

        adminService.delete(id);
        return noContent().build();
    }

    private URI getCreatedUri(final Long id) {

        return URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/item/get/{id}")
                .build(id)
                .toString()
        );

    }
}
