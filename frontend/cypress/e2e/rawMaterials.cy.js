describe("Raw Material Management", () => {
  beforeEach(() => {
    cy.visit("/raw-materials");
  });

  it("should create a new raw material", () => {
    cy.contains("Add New Raw Material").click();
    cy.url().should("include", "/raw-materials/new");

    cy.get('input[type="text"]').type("Steel");
    cy.get('input[type="number"]').type("1000");

    cy.contains("Create Raw Material").click();
    cy.url().should("eq", Cypress.config().baseUrl + "/raw-materials");
  });
});
