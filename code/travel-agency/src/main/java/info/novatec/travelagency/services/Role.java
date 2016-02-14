package info.novatec.travelagency.services;

public enum Role {
    TRAVEL_AGENT, ADMIN, GUEST;

    @Override
    public String toString() {
        return name();
    }
}
