describe("Inventory Management System", () => {
  beforeEach(() => {
    cy.visit("/");
  });

  it("should load the home page", () => {
    cy.contains("Welcome to Inventory Management System").should("be.visible");
  });

  it("should navigate to products page", () => {
    cy.contains("Products").click();
    cy.url().should("include", "/products");
    cy.contains("Products").should("be.visible");
  });

  it("should navigate to raw materials page", () => {
    cy.contains("Raw Materials").click();
    cy.url().should("include", "/raw-materials");
    cy.contains("Raw Materials").should("be.visible");
  });

  it("should navigate to production report page", () => {
    cy.contains("Production Report").click();
    cy.url().should("include", "/production");
    cy.contains("Production Report").should("be.visible");
  });
});
